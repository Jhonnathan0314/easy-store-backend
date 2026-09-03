package com.easy.store.backend.context.store_request.application.service;

import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.ForbiddenActionException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Application-level authorization service for the StoreRequest context.
 * <p>
 * SecurityConfig ya restringe /pending, /approve y /reject a ADMIN, pero no puede expresar
 * "solo el propio dueño del recurso" para GET /api/v1/store-request/user/{userId}: sin esto,
 * cualquier usuario autenticado podia consultar las solicitudes de tienda de CUALQUIER otro
 * usuario cambiando el userId en la URL. Sigue el mismo patron que
 * {@code PurchaseAuthorizationService} para el contexto purchase.
 * <p>
 * Reglas:
 * <ul>
 *     <li><b>admin</b>: acceso sin restriccion.</li>
 *     <li>cualquier otro rol (client/owner/ghost): solo puede consultar sus propias solicitudes,
 *     es decir userId debe coincidir con el id del usuario autenticado.</li>
 * </ul>
 */
@Service
public class StoreRequestAuthorizationService {

    private static final String ADMIN = "admin";

    public void authorizeFindByUserId(Long userId) throws ForbiddenActionException {
        User user = currentUser();
        if (isAdmin(user)) return;
        if (Objects.equals(user.getId(), userId)) return;
        throw forbidden();
    }

    private boolean isAdmin(User user) {
        return user.getRole() != null && ADMIN.equalsIgnoreCase(user.getRole().getName());
    }

    private User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new IllegalStateException("No authenticated user found in security context");
        }
        return (User) authentication.getPrincipal();
    }

    private ForbiddenActionException forbidden() {
        return new ForbiddenActionException(ErrorMessages.FORBIDDEN_ACTION);
    }

}
