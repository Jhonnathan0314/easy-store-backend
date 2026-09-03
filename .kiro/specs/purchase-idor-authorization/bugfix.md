# Bugfix Requirements Document

## Introduction

El módulo de compras (`/api/v1/purchase/**` y `/api/v1/purchase-has-product/**`) tiene una
vulnerabilidad de tipo IDOR (Insecure Direct Object Reference). `SecurityConfig` sólo restringe
por rol los verbos `PUT` y `DELETE` de `/api/v1/purchase/**`; todos los `GET`, el `POST` (generate)
y la totalidad de `/api/v1/purchase-has-product/**` caen en la regla genérica
`anyRequest().authenticated()`. Esto significa que cualquier usuario autenticado — incluyendo
roles `client` y `ghost`, que sólo deberían ver sus propias compras — puede leer o modificar
compras, cuentas o usuarios ajenos simplemente cambiando un id en la URL o en el cuerpo de la
petición. El impacto es exposición de datos de compra de terceros (montos, productos, tipo de
pago) y manipulación de líneas de carrito de compras que no pertenecen al usuario autenticado.

## Bug Analysis

### Current Behavior (Defect)

1.1 WHEN un usuario autenticado con rol `owner` solicita `GET /api/v1/purchase/account/{accountId}` con un `accountId` que no pertenece a ninguna de sus cuentas (tabla `account_has_user`) THEN el sistema responde `200 OK` con las compras de esa cuenta ajena.

1.2 WHEN un usuario autenticado con rol `client` o `ghost` solicita `GET /api/v1/purchase/user/{userId}` con un `userId` distinto al `user_id` de su propio token THEN el sistema responde `200 OK` con las compras de ese usuario ajeno.

1.3 WHEN un usuario autenticado con rol `owner` solicita `GET /api/v1/purchase/user/{userId}` con un `userId` que no pertenece a ninguna cuenta asociada a él THEN el sistema responde `200 OK` con las compras de ese usuario ajeno.

1.4 WHEN un usuario autenticado con rol `client` o `ghost` solicita `GET /api/v1/purchase/{id}` de una compra cuyo `purchase.user.id` no coincide con su propio `user_id` THEN el sistema responde `200 OK` con el detalle de la compra ajena.

1.5 WHEN un usuario autenticado con rol `owner` solicita `GET /api/v1/purchase/{id}` de una compra cuya categoría pertenece a una cuenta no asociada a él THEN el sistema responde `200 OK` con el detalle de la compra ajena.

1.6 WHEN un usuario autenticado con rol `client` o `ghost` solicita `GET /api/v1/purchase/category/{categoryId}` o `GET /api/v1/purchase/account/{accountId}` THEN el sistema responde `200 OK` (estos endpoints no deberían aplicar en absoluto a compradores).

1.7 WHEN un usuario autenticado con rol `owner` solicita `GET /api/v1/purchase/category/{categoryId}` con una categoría de una cuenta ajena THEN el sistema responde `200 OK` con las compras de esa categoría ajena.

1.8 WHEN cualquier usuario autenticado (no admin) solicita `GET /api/v1/purchase` (findAll) THEN el sistema responde `200 OK` con las compras de todas las cuentas y usuarios del sistema.

1.9 WHEN un usuario autenticado envía `POST /api/v1/purchase` (generate) con un `userId` en el cuerpo distinto a su propio `user_id` THEN el sistema genera la compra a nombre del usuario ajeno respondiendo `200 OK`.

1.10 WHEN un usuario autenticado invoca cualquier operación de `/api/v1/purchase-has-product/**` (add, addAll, update, delete individual o en lote) referenciando un `purchaseId` de una compra que no le pertenece (ni por cuenta si es owner, ni por usuario si es client/ghost) THEN el sistema ejecuta la operación y responde `200 OK` / `204 No Content` sin validar la propiedad de la compra.

### Expected Behavior (Correct)

2.1 WHEN un usuario autenticado con rol `owner` solicita `GET /api/v1/purchase/account/{accountId}` con un `accountId` que no está en su `account_has_user` THEN el sistema SHALL responder `403 Forbidden`.

2.2 WHEN un usuario autenticado con rol `client` o `ghost` solicita `GET /api/v1/purchase/user/{userId}` con `userId` distinto a su propio `user_id` THEN el sistema SHALL responder `403 Forbidden`.

2.3 WHEN un usuario autenticado con rol `owner` solicita `GET /api/v1/purchase/user/{userId}` con un `userId` que no comparte ninguna cuenta con él THEN el sistema SHALL responder `403 Forbidden`.

2.4 WHEN un usuario autenticado con rol `client` o `ghost` solicita `GET /api/v1/purchase/{id}` de una compra cuyo `purchase.user.id` no coincide con su `user_id` THEN el sistema SHALL responder `403 Forbidden`.

2.5 WHEN un usuario autenticado con rol `owner` solicita `GET /api/v1/purchase/{id}` de una compra cuya categoría pertenece a una cuenta no asociada a él THEN el sistema SHALL responder `403 Forbidden`.

2.6 WHEN un usuario autenticado con rol `client` o `ghost` solicita `GET /api/v1/purchase/category/{categoryId}` o `GET /api/v1/purchase/account/{accountId}` THEN el sistema SHALL responder `403 Forbidden` siempre, sin importar los ids.

2.7 WHEN un usuario autenticado con rol `owner` solicita `GET /api/v1/purchase/category/{categoryId}` con una categoría de una cuenta no asociada a él THEN el sistema SHALL responder `403 Forbidden`.

2.8 WHEN un usuario autenticado sin rol `admin` solicita `GET /api/v1/purchase` (findAll) THEN el sistema SHALL responder `403 Forbidden`.

2.9 WHEN un usuario autenticado sin rol `admin` envía `POST /api/v1/purchase` (generate) con un `userId` en el cuerpo distinto a su propio `user_id` THEN el sistema SHALL responder `403 Forbidden` sin generar la compra.

2.10 WHEN un usuario autenticado invoca cualquier operación de `/api/v1/purchase-has-product/**` referenciando uno o más `purchaseId` que no le pertenecen (por la misma regla de propiedad del punto 2.4/2.5) THEN el sistema SHALL responder `403 Forbidden` y SHALL rechazar la operación completa (incluyendo lotes donde sólo una línea referencia una compra ajena), sin aplicar cambios parciales.

2.11 WHEN la denegación ocurre por falta de propiedad sobre un recurso que sí existe THEN el sistema SHALL responder con el mismo formato `ApiResponse`/`ErrorMessage` ya usado por `ControllerException`/`AuthorizationError`, con un mensaje genérico que no confirme ni niegue la existencia del recurso.

2.12 WHEN se solicita `GET /api/v1/purchase/{id}` (o una operación de purchase-has-product) sobre un id de compra que no existe THEN el sistema SHALL responder `404 Not Found` (comportamiento sin cambios), nunca `403`, para no revelar mediante el código de estado si el recurso existe.

### Unchanged Behavior (Regression Prevention)

3.1 WHEN un usuario con rol `admin` invoca cualquier endpoint de `/api/v1/purchase/**` o `/api/v1/purchase-has-product/**` con cualquier id THEN el sistema SHALL CONTINUE TO responder sin restricciones de propiedad (sólo sujeto a existencia de los recursos).

3.2 WHEN un usuario con rol `owner` solicita compras de una cuenta, usuario o categoría que sí está asociada a él THEN el sistema SHALL CONTINUE TO responder `200 OK` con los datos correspondientes.

3.3 WHEN un usuario con rol `client` o `ghost` solicita `GET /api/v1/purchase/user/{userId}` o `GET /api/v1/purchase/{id}` sobre sus propios recursos (`userId` = su `user_id`, o `purchase.user.id` = su `user_id`) THEN el sistema SHALL CONTINUE TO responder `200 OK`.

3.4 WHEN un usuario con rol `client` o `ghost` (incluyendo el usuario ghost preconfigurado para compras sin login real) genera una compra (`POST /api/v1/purchase`) con su propio `userId` en el cuerpo THEN el sistema SHALL CONTINUE TO generarla normalmente.

3.5 WHEN un usuario (de cualquier rol permitido) invoca operaciones de `/api/v1/purchase-has-product/**` sobre líneas de una compra que sí le pertenece THEN el sistema SHALL CONTINUE TO ejecutar la operación (add, addAll, update, delete individual o en lote) exactamente como antes, incluyendo el recálculo del total de la compra.

3.6 WHEN se invoca `PUT` o `DELETE` sobre `/api/v1/purchase/**` con un rol distinto de `ADMIN`/`OWNER` THEN el sistema SHALL CONTINUE TO responder `403 Forbidden` a nivel de `SecurityConfig`, sin cambios respecto al comportamiento actual.

3.7 WHEN cualquier solicitud a `/api/v1/purchase/**` o `/api/v1/purchase-has-product/**` referencia un recurso que no existe (compra, categoría, cuenta, usuario o producto inexistente) THEN el sistema SHALL CONTINUE TO responder con los mismos códigos y mensajes de error (`404`/`400`) ya definidos por los casos de uso existentes, sin que la nueva validación de autorización los enmascare.

### Bug Condition (formalización)

```pascal
FUNCTION isBugCondition(request)
  INPUT: request of type PurchaseRequest
         (role, requesterUserId, requesterAccountIds[], targetResourceOwnerAccountId,
          targetResourceOwnerUserId, endpoint)
  OUTPUT: boolean

  RETURN request.role <> 'admin'
         AND (
            (request.endpoint IN ['findAll'])
            OR (request.role IN ['client', 'ghost'] AND request.endpoint IN
                ['findByAccountId', 'findByCategoryId'])
            OR (request.role = 'owner' AND request.endpoint IN
                ['findByAccountId', 'findByCategoryId', 'findByUserId', 'findById',
                 'purchaseHasProduct']
                AND request.targetResourceOwnerAccountId NOT IN request.requesterAccountIds)
            OR (request.role IN ['client', 'ghost'] AND request.endpoint IN
                ['findByUserId', 'findById', 'purchaseHasProduct']
                AND request.targetResourceOwnerUserId <> request.requesterUserId)
            OR (request.endpoint = 'generate'
                AND request.bodyUserId <> request.requesterUserId)
         )
         // y la petición NO recibe hoy un 403 (i.e. la autorización actual del SecurityConfig
         // la deja pasar como "authenticated")
END FUNCTION
```

### Property (comportamiento esperado para inputs con bug)

```pascal
// Property: Fix Checking
FOR ALL request WHERE isBugCondition(request) DO
  response ← handlePurchaseRequest'(request)
  ASSERT response.status = 403
     AND response.body.error.detail = "No tienes permisos para acceder a este recurso."
     AND no_side_effects_applied(response)
END FOR
```

### Preservation Goal

```pascal
// Property: Preservation Checking
FOR ALL request WHERE NOT isBugCondition(request) DO
  ASSERT handlePurchaseRequest(request) = handlePurchaseRequest'(request)
END FOR
```
