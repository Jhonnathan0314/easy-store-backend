# Implementation Plan

- [x] 1. Write bug condition exploration test
  - **Property 1: Bug Condition** - Acceso cruzado (IDOR) en compras
  - **CRITICAL**: Este test DEBE FALLAR sobre el código sin el fix (confirma que el bug existe)
  - **GOAL**: Demostrar que un solicitante no propietario (owner de otra cuenta, client/ghost de
    otro usuario, o cualquier no-admin en `findAll`) recibe hoy una respuesta exitosa en vez de
    `403 Forbidden`.
  - **Scoped PBT Approach**: bug determinista por rol/relación de propiedad; se escenifica con
    casos concretos: owner de cuenta 1 pidiendo cuenta 2, client `user_id=5` pidiendo usuario 9,
    client `user_id=5` pidiendo compra 12 (dueña del usuario 9), no-admin pidiendo `findAll`.
  - Test implementation: se invoca directamente `PurchaseController`/`PurchaseHasProductController`
    con el caso de uso mockeado devolviendo datos, sin ninguna llamada a
    `PurchaseAuthorizationService` (porque aún no existe). Se documenta que la llamada al caso de
    uso ocurre sin restricción.
  - Run test on UNFIXED code → **EXPECTED OUTCOME**: no hay excepción, el handler devuelve 200 con
    los datos (confirma el IDOR).
  - Documentar contraejemplos: "owner(accountId=1) obtiene 200 al pedir purchases de accountId=2";
    "client(userId=5) obtiene 200 al pedir purchases de userId=9"; etc.
  - _Requirements: 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 1.10_

- [x] 2. Write preservation tests (BEFORE implementing fix)
  - **Property 2: Preservation** - Acceso legítimo de admin/owner/client/ghost sobre sus propios
    recursos
  - **IMPORTANT**: metodología observation-first: sobre el código actual (sin fix), invocar los
    handlers con admin, owner-sobre-su-cuenta y client-sobre-su-compra y observar que responden
    `200 OK` delegando siempre al caso de uso.
  - Test cases: admin llamando cada endpoint con ids arbitrarios; owner con `accountId`/`userId`/
    `categoryId` que sí están en su `account_has_user`; client/ghost con `userId`/`purchaseId`
    igual a su propio `user_id`; generate con `userId` del body igual al propio.
  - Verify tests PASS on UNFIXED code (hoy no hay ninguna verificación, así que estos casos ya
    pasan; sirven de línea base para detectar regresiones tras el fix).
  - _Requirements: 3.1, 3.2, 3.3, 3.4, 3.5, 3.6, 3.7_

- [x] 3. Fix para el IDOR en el módulo de compras

  - [x] 3.1 Implementar `ForbiddenActionException` y su mapeo a 403
    - Crear `ForbiddenActionException` en `utils.exceptions`.
    - Agregar `ErrorMessages.FORBIDDEN_ACTION`.
    - Agregar `@ExceptionHandler(ForbiddenActionException.class)` en `ControllerException` →
      `403 Forbidden` con el mismo `ApiResponse`/`ErrorMessage` que el resto.
    - _Requirements: 2.11_

  - [x] 3.2 Implementar `PurchaseAuthorizationService`
    - Métodos: `authorizeFindAll`, `authorizeFindByAccountId`, `authorizeFindByUserId`,
      `authorizeFindByCategoryId`, `authorizePurchaseAccess`, `authorizePurchaseHasProductIds`,
      `authorizeGenerate`.
    - Resuelve identidad desde `SecurityContextHolder` (principal `User`, con `id` y `role`).
    - Resuelve propiedad vía `AccountHasUserRepository`, `CategoryRepository`,
      `PurchaseRepository` (sin tocar `JwtAuthenticationFilter`/`JwtService`).
    - _Bug_Condition: isBugCondition(request) del design_
    - _Expected_Behavior: Property 1 del design_
    - _Preservation: Property 2 del design_
    - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.7, 2.8, 2.9, 2.10, 2.12, 3.1, 3.2, 3.3, 3.4_

  - [x] 3.3 Conectar `PurchaseAuthorizationService` en `PurchaseController`
    - `findAll`, `findById`, `findByAccountId`, `findByCategoryId`, `findByUserId`, `generate`
      invocan el método de autorización correspondiente antes del caso de uso.
    - _Bug_Condition: isBugCondition(request) del design_
    - _Expected_Behavior: Property 1 del design_
    - _Preservation: Property 2 del design_
    - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.7, 2.8, 2.9, 2.12, 3.1, 3.2, 3.3, 3.4_

  - [x] 3.4 Conectar `PurchaseAuthorizationService` en `PurchaseHasProductController`
    - `add`, `update`, `deleteByPurchaseIdAndProductId` invocan `authorizePurchaseAccess` con el
      `purchaseId` correspondiente; `addAll`/`removeAll` invocan
      `authorizePurchaseHasProductIds` sobre el lote completo antes de tocar el caso de uso.
    - _Bug_Condition: isBugCondition(request) del design_
    - _Expected_Behavior: Property 1 del design_
    - _Preservation: Property 2 del design_
    - _Requirements: 2.10, 2.12, 3.5_

  - [x] 3.5 Verificar que el test de exploración ahora pasa (bug corregido)
    - **Property 1: Expected Behavior** - Acceso cruzado (IDOR) en compras
    - **IMPORTANT**: re-ejecutar los MISMOS escenarios de la tarea 1, ahora contra
      `PurchaseAuthorizationService` real (no mockeado) integrado en los controllers.
    - **EXPECTED OUTCOME**: todos los escenarios ahora lanzan `ForbiddenActionException` /
      responden `403`.
    - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.7, 2.8, 2.9, 2.10, 2.12_

  - [x] 3.6 Verificar que los tests de preservación siguen pasando
    - **Property 2: Preservation** - Acceso legítimo de admin/owner/client/ghost
    - **IMPORTANT**: re-ejecutar los MISMOS escenarios de la tarea 2.
    - **EXPECTED OUTCOME**: siguen pasando (sin excepción, mismo código/cuerpo de respuesta que
      antes del fix).
    - Confirmar además que un `purchaseId`/recurso inexistente sigue devolviendo `404`
      (`NoResultsException`), nunca `403`.

- [x] 4. Checkpoint - Ensure all tests pass
  - `./mvnw clean compile` sin errores.
  - `./mvnw test` (o el subconjunto relacionado a purchase/purchase_has_product/security) en
    verde.
  - Revisar manualmente que ningún flujo legítimo (ghost comprando, owner gestionando su tienda,
    admin viendo todo) quedó bloqueado por error.
