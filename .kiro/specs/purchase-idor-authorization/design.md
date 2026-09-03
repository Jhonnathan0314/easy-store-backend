# Purchase IDOR Authorization Bugfix Design

## Overview

`SecurityConfig` sólo puede expresar reglas basadas en rol por verbo HTTP (`hasAnyAuthority`).
No puede saber, a nivel de filtro, si el `accountId`/`userId`/`categoryId`/`purchaseId` que viaja
en la URL o en el cuerpo de una petición pertenece al usuario autenticado. Por eso hoy toda
petición autenticada — sin importar el rol — pasa la capa de `SecurityConfig` y llega al
controller, que ejecuta el caso de uso sin ninguna comprobación de propiedad (IDOR).

La solución agrega una capa de autorización de aplicación, `PurchaseAuthorizationService`
(hexagonal: vive en `context/purchase/application/service`, sólo depende de puertos de dominio:
`AccountHasUserRepository`, `CategoryRepository`, `PurchaseRepository`). Los controllers
(`PurchaseController`, `PurchaseHasProductController`) invocan este servicio ANTES de delegar a
los casos de uso existentes. Si la comprobación falla, se lanza `ForbiddenActionException`
(nueva, en `utils.exceptions`), que `ControllerException` mapea a `403` reusando el mismo
`ApiResponse`/`ErrorMessage` que ya usa `AuthorizationError` para los 403 de Spring Security.

`SecurityConfig` no cambia sus reglas por rol existentes (siguen siendo correctas para lo que
cubren: bloquear PUT/DELETE de purchase a roles no ADMIN/OWNER). La autorización fina de
propiedad vive exclusivamente en el nuevo servicio de aplicación.

## Glossary

- **Bug_Condition (C)**: la petición proviene de un usuario no-admin cuyo rol/relación de
  propiedad no cubre el recurso solicitado (cuenta, usuario, categoría o compra), pero hoy
  `SecurityConfig` la deja pasar como "authenticated".
- **Property (P)**: la petición SHALL ser rechazada con `403 Forbidden` (mismo formato de
  `ApiResponse`/`ErrorMessage`) y SHALL NOT producir efectos (lectura ni escritura) sobre el
  recurso ajeno.
- **Preservation**: toda petición donde el solicitante sí es propietario del recurso (o es
  admin) SHALL CONTINUE TO comportarse exactamente igual que hoy (mismos códigos, mismo cuerpo).
- **PurchaseAuthorizationService**: nuevo servicio de aplicación que centraliza las
  comprobaciones de propiedad para el contexto `purchase` y `purchase_has_product`.
- **owner-account relationship**: se resuelve vía `AccountHasUserRepository.findByUserId(userId)`
  → lista de `AccountHasUser`, cada uno con `getAccountId().getId()`.
- **purchase ownership (owner role)**: se resuelve transitivamente:
  `purchase.category.id` → `CategoryRepository.findById` → `category.account.id` → ¿está esa
  cuenta en `account_has_user` del owner?
- **purchase ownership (client/ghost role)**: `purchase.user.id == token.user_id`.
- **ghost user**: usuario preconfigurado (`easy.store.ghost.user`) sin cuenta propia; su rol es
  `ghost` y, para efectos de esta corrección, se le aplican exactamente las mismas reglas que a
  `client` (propiedad por `user_id`).

## Bug Details

### Bug Condition

**Formal Specification:**
```
FUNCTION isBugCondition(request)
  INPUT: request of type PurchaseRequest
  OUTPUT: boolean

  RETURN request.callerRole <> 'admin'
    AND (
      request.endpoint = 'findAll'
      OR (request.endpoint = 'findByAccountId' AND
          (request.callerRole IN ['client','ghost']
           OR NOT ownsAccount(request.callerUserId, request.accountId)))
      OR (request.endpoint = 'findByCategoryId' AND
          (request.callerRole IN ['client','ghost']
           OR NOT ownsAccount(request.callerUserId, accountOfCategory(request.categoryId))))
      OR (request.endpoint = 'findByUserId' AND
          ((request.callerRole = 'owner' AND NOT sharesAccount(request.callerUserId, request.userId))
           OR (request.callerRole IN ['client','ghost'] AND request.userId <> request.callerUserId)))
      OR (request.endpoint IN ['findById','purchaseHasProductOp'] AND
          ((request.callerRole = 'owner' AND NOT ownsAccount(request.callerUserId, accountOfPurchase(request.purchaseId)))
           OR (request.callerRole IN ['client','ghost'] AND ownerOfPurchase(request.purchaseId) <> request.callerUserId)))
      OR (request.endpoint = 'generate' AND request.bodyUserId <> request.callerUserId)
    )
END FUNCTION
```

### Examples

- Un `owner` de la cuenta 1 hace `GET /api/v1/purchase/account/2` → hoy `200 OK` con las compras
  de la cuenta 2 (ajena). Debería ser `403`.
- Un `client` con `user_id=5` hace `GET /api/v1/purchase/user/9` → hoy `200 OK` con las compras
  del usuario 9. Debería ser `403`.
- Un `client` con `user_id=5` hace `GET /api/v1/purchase/12`, donde la compra 12 pertenece al
  usuario 9 → hoy `200 OK` con el detalle. Debería ser `403`.
- Un `owner` sin ninguna cuenta asociada hace `GET /api/v1/purchase` (findAll) → hoy `200 OK` con
  TODAS las compras del sistema. Debería ser `403` (findAll es admin-only).
- Caso límite: `GET /api/v1/purchase/999999` donde la compra no existe → SHALL seguir siendo
  `404`, no `403`, para no filtrar existencia.
- Caso límite: lote `PATCH /api/v1/purchase-has-product/add/all` con 3 líneas, donde 2
  pertenecen a una compra propia y 1 a una compra ajena → SHALL rechazar TODO el lote con `403`
  (no aplicar las 2 líneas legítimas).

## Expected Behavior

### Preservation Requirements

**Unchanged Behaviors:**
- Un `admin` sigue teniendo acceso sin restricciones de propiedad a todos los endpoints de
  purchase y purchase-has-product.
- Un `owner` sigue viendo/gestionando sin cambios las compras de sus propias cuentas.
- Un `client`/`ghost` sigue viendo/gestionando sin cambios sus propias compras y generando
  compras a su propio nombre (incluye el flujo de compra sin login real del usuario ghost).
- Las reglas de `SecurityConfig` para PUT/DELETE de `/api/v1/purchase/**` (sólo ADMIN/OWNER)
  no cambian.
- Los códigos y mensajes de error por recursos inexistentes (`404`/`400` de
  `NoResultsException`, `InvalidBodyException`, etc.) no cambian.
- El recálculo de totales (`recalculateTotal`) tras add/update/remove de líneas sigue
  ejecutándose igual para operaciones legítimas.

**Scope:**
Todas las peticiones donde el solicitante es dueño del recurso (o es admin) están fuera del
alcance de este fix: deben producir exactamente la misma respuesta que hoy.

## Hypothesized Root Cause

1. **Ausencia de reglas de autorización por recurso en `SecurityConfig`**: `SecurityConfig` sólo
   puede expresar "rol X puede invocar verbo Y sobre el path Z"; no tiene acceso al cuerpo/ids
   del negocio para decidir propiedad. Los GET de purchase y todo purchase-has-product caen en
   `anyRequest().authenticated()`.

2. **El JWT lleva `user_id`/`account_id`/`user_role` pero no se usan tras la autenticación**:
   `JwtAuthenticationFilter` sólo puebla el `SecurityContext` con el `User` (username + rol vía
   `getAuthorities()`), pero ninguna capa posterior consulta esos claims para comparar contra los
   ids de la URL/cuerpo.

3. **Ningún controller ni caso de uso valida propiedad**: los casos de uso (`FindByIdPurchaseUseCase`,
   `FindByAccountIdPurchaseUseCase`, etc.) sólo validan existencia, nunca "¿este solicitante puede
   ver este recurso?". Es responsabilidad de una capa de autorización que hoy no existe.

4. **`PurchaseHasProductController` no tiene ninguna regla ni en `SecurityConfig` ni en el
   controller**: cualquier usuario autenticado puede invocar add/update/delete sobre cualquier
   `purchaseId`/`productId`.

## Correctness Properties

Property 1: Bug Condition - Acceso cruzado (IDOR) en compras

_For any_ petición sobre `/api/v1/purchase/**` o `/api/v1/purchase-has-product/**` donde el rol
del solicitante no es `admin` y el recurso solicitado (cuenta, usuario, categoría o compra) no le
pertenece según las reglas de propiedad (owner vía `account_has_user`, client/ghost vía
`user_id`), la función corregida SHALL responder `403 Forbidden` con el formato
`ApiResponse`/`ErrorMessage` estándar, y SHALL NOT ejecutar el caso de uso subyacente (sin
lectura ni escritura del recurso ajeno). Si el recurso simplemente no existe, SHALL responder
`404 Not Found` en su lugar (nunca revelar existencia vía el código de estado).

**Validates: Requirements 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.7, 2.8, 2.9, 2.10, 2.11, 2.12**

Property 2: Preservation - Acceso legítimo de admin/owner/client/ghost sobre sus propios recursos

_For any_ petición sobre `/api/v1/purchase/**` o `/api/v1/purchase-has-product/**` donde el
solicitante es `admin`, o es `owner` sobre una cuenta/categoría/compra asociada a él, o es
`client`/`ghost` sobre su propia compra (por `user_id`), la función corregida SHALL producir
exactamente la misma respuesta (código, cuerpo, efectos) que la función original, preservando el
comportamiento legítimo actual, incluyendo el flujo de compra del usuario ghost.

**Validates: Requirements 3.1, 3.2, 3.3, 3.4, 3.5, 3.6, 3.7**

## Fix Implementation

### Changes Required

**File**: `src/main/java/com/easy/store/backend/utils/exceptions/ForbiddenActionException.java` (nuevo)
- Excepción de dominio para denegaciones de autorización de aplicación.

**File**: `src/main/java/com/easy/store/backend/security/config/ControllerException.java`
- Nuevo `@ExceptionHandler(ForbiddenActionException.class)` → `403 Forbidden`, mismo formato
  `ApiResponse`/`ErrorMessage` que el resto de handlers.

**File**: `src/main/java/com/easy/store/backend/utils/constants/ErrorMessages.java`
- Nueva constante `FORBIDDEN_ACTION` con mensaje genérico ("No tienes permisos para acceder a
  este recurso."), igual al que ya usa `AuthorizationError` para los 403 de Spring Security, así
  la respuesta es indistinguible para el cliente sin importar en qué capa se generó.

**File**: `src/main/java/com/easy/store/backend/context/purchase/application/service/PurchaseAuthorizationService.java` (nuevo)
- Servicio de aplicación con un método de autorización por endpoint:
  - `authorizeFindAll()` — sólo admin.
  - `authorizeFindByAccountId(accountId)` — admin sin restricción; owner sólo si
    `ownsAccount(callerId, accountId)`; client/ghost siempre 403.
  - `authorizeFindByUserId(userId)` — admin sin restricción; owner sólo si
    `sharesAccount(callerId, userId)`; client/ghost sólo si `userId == callerId`.
  - `authorizeFindByCategoryId(categoryId)` — admin sin restricción; owner sólo si la cuenta de
    la categoría es suya; client/ghost siempre 403.
  - `authorizePurchaseAccess(purchaseId, notFoundMessage)` — resuelve la compra (404 si no
    existe), y aplica: admin sin restricción; owner sólo si la cuenta de la categoría de la
    compra es suya; client/ghost sólo si `purchase.user.id == callerId`. Devuelve la `Purchase`
    ya resuelta para reutilizarla en el controller si aplica.
  - `authorizePurchaseHasProductIds(ids)` — aplica `authorizePurchaseAccess` a cada `purchaseId`
    distinto referenciado por un lote; si cualquiera falla, se detiene y propaga el 403/404 (el
    lote completo se rechaza antes de tocar el repositorio).
  - `authorizeGenerate(bodyUserId)` — admin sin restricción; el resto sólo si
    `bodyUserId == callerId`.
- Obtiene el usuario autenticado (con rol e id) desde
  `SecurityContextHolder.getContext().getAuthentication().getPrincipal()`, que ya es una
  instancia de `User` (poblada por `JwtAuthenticationFilter` con los datos de BD, incluyendo
  `role`). No fue necesario modificar `JwtAuthenticationFilter` ni `JwtService`: el `User` del
  `SecurityContext` ya tiene `id` y `role.name`, que es toda la información de identidad que la
  regla de negocio necesita; la relación cuenta-usuario se resuelve con `AccountHasUserRepository`
  (ya existente) en el momento de la petición en lugar de depender de claims del JWT.

**File**: `src/main/java/com/easy/store/backend/context/purchase/presentation/controller/PurchaseController.java`
- Se invoca el método de autorización correspondiente al inicio de cada handler, antes de
  delegar al caso de uso. `findById` reutiliza `authorizePurchaseAccess` (que ya valida
  existencia) pero sigue llamando a `findByIdPurchaseUseCase.findById` para no duplicar el mapeo
  de productos asociados que hace el caso de uso.

**File**: `src/main/java/com/easy/store/backend/context/purchase_has_product/presentation/controller/PurchaseHasProductController.java`
- `add`/`update`/`deleteByPurchaseIdAndProductId` (individual) invocan
  `authorizePurchaseAccess(purchaseId, ...)` con el `purchaseId` del cuerpo o del path.
- `addAll`/`removeAll` (lote) invocan `authorizePurchaseHasProductIds(ids)` sobre los
  `purchaseId` distintos del lote completo antes de llamar al caso de uso.

## Testing Strategy

### Validation Approach

Primero se escriben tests de exploración que reproducen el acceso cruzado sobre el código SIN
el fix (deben fallar, es decir, hoy devuelven 200/204 en lugar de 403). Luego se documentan los
comportamientos legítimos observados hoy (preservación) y se escriben tests que los capturan.
Finalmente se implementa el fix y se verifica que ambos grupos de tests pasan.

### Exploratory Bug Condition Checking

**Goal**: Demostrar, contra el código actual (sin `PurchaseAuthorizationService` invocado desde
los controllers), que un solicitante no propietario obtiene 200/204 en vez de 403.

**Test Plan**: Tests unitarios sobre los controllers (Mockito, sin `SecurityConfig`/MockMvc)
simulando un `Authentication` en `SecurityContextHolder` con un `User` de rol `owner`/`client`
que no es dueño del recurso solicitado, e invocando el handler directamente. Antes del fix, el
handler llama al caso de uso (mockeado para devolver datos) sin ninguna verificación intermedia.

**Test Cases**:
1. **Owner solicita cuenta ajena**: owner de cuenta 1 pide `findByAccountId(2)` (no falla antes
   del fix porque no existe verificación).
2. **Client solicita usuario ajeno**: client con `user_id=5` pide `findByUserId(9)`.
3. **Client solicita compra ajena por id**: client con `user_id=5` pide `findById(12)` donde la
   compra 12 es del usuario 9.
4. **Cualquier no-admin pide findAll**: debería estar limitado a admin.

**Expected Counterexamples**: antes del fix, el caso de uso mockeado se invoca sin restricción
(el mock de `PurchaseRepository`/`AccountHasUserRepository` nunca se consulta para autorizar).

### Fix Checking

**Pseudocode:**
```
FOR ALL request WHERE isBugCondition(request) DO
  result := PurchaseAuthorizationService'.authorize(request)
  ASSERT result throws ForbiddenActionException
END FOR
```

### Preservation Checking

**Pseudocode:**
```
FOR ALL request WHERE NOT isBugCondition(request) DO
  ASSERT PurchaseAuthorizationService.authorize(request) does not throw
     AND controllerHandler(request) produces the same response as before the fix
END FOR
```

**Testing Approach**: Se usan tests unitarios deterministas (no PBT) dado que este proyecto no
tiene una librería de property-based testing configurada (no se encontró jqwik/quicktheories en
`pom.xml`) y el dominio de "roles x relaciones de propiedad" es finito y pequeño; se cubre con
tests parametrizados por rol y por relación (propio/ajeno), que es equivalente en cobertura a una
propiedad universal sobre ese dominio finito.

**Test Cases**:
1. **Admin sin restricción**: admin invoca cada endpoint con ids arbitrarios → no debe lanzar
   `ForbiddenActionException`.
2. **Owner sobre su propia cuenta/categoría/usuario/compra**: no debe lanzar excepción.
3. **Client/ghost sobre su propia compra/usuario**: no debe lanzar excepción.
4. **Generate con userId propio**: no debe lanzar excepción (incluye rol ghost).
5. **Recurso inexistente**: `authorizePurchaseAccess` con un `purchaseId` inexistente lanza
   `NoResultsException`, nunca `ForbiddenActionException`.

### Unit Tests

- `PurchaseAuthorizationServiceTest`: cubre los 5 métodos públicos con matrices rol × propiedad
  (admin/owner/client/ghost × propio/ajeno/inexistente).
- `PurchaseControllerTest` / `PurchaseHasProductControllerTest`: verifican que cada handler
  invoca el método de autorización correcto y que una `ForbiddenActionException` se propaga sin
  llamar al caso de uso subyacente.

### Property-Based Tests

No aplica (ver justificación en Preservation Checking); se usan tests parametrizados
(`@ParameterizedTest`) sobre la matriz rol × propiedad como equivalente práctico.

### Integration Tests

Fuera de alcance de este fix (no hay infraestructura de integración con base de datos real en el
proyecto para este módulo); la cobertura se concentra en tests unitarios de
`PurchaseAuthorizationService` y de los controllers con Mockito, siguiendo el estilo ya usado en
`PurchaseRepositoryJpaAdapterTest`.
