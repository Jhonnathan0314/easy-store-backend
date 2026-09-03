package com.easy.store.backend.context.store_request.application.service;

import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.utils.exceptions.ForbiddenActionException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Cierra el mismo hueco de IDOR que {@code PurchaseAuthorizationServiceTest} pero para
 * StoreRequest: sin esta validacion, cualquier usuario autenticado podia consultar las
 * solicitudes de tienda de otro usuario cambiando el userId en la URL.
 */
class StoreRequestAuthorizationServiceTest {

    private final StoreRequestAuthorizationService service = new StoreRequestAuthorizationService();

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    private void authenticateAs(Long userId, String roleName) {
        User user = User.builder()
                .id(userId)
                .username("user-" + userId)
                .role(Role.builder().id(1L).name(roleName).build())
                .build();
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
        );
    }

    @Test
    void client_requestingOtherUsersRequests_isForbidden() {
        authenticateAs(5L, "client");
        assertThatThrownBy(() -> service.authorizeFindByUserId(9L))
                .isInstanceOf(ForbiddenActionException.class);
    }

    @Test
    void owner_requestingOtherUsersRequests_isForbidden() {
        authenticateAs(5L, "owner");
        assertThatThrownBy(() -> service.authorizeFindByUserId(9L))
                .isInstanceOf(ForbiddenActionException.class);
    }

    @Test
    void ghost_requestingOtherUsersRequests_isForbidden() {
        authenticateAs(5L, "ghost");
        assertThatThrownBy(() -> service.authorizeFindByUserId(9L))
                .isInstanceOf(ForbiddenActionException.class);
    }

    @Test
    void client_requestingOwnRequests_isAllowed() {
        authenticateAs(5L, "client");
        assertThatCode(() -> service.authorizeFindByUserId(5L)).doesNotThrowAnyException();
    }

    @Test
    void admin_requestingAnyUsersRequests_isAllowed() {
        authenticateAs(1L, "admin");
        assertThatCode(() -> service.authorizeFindByUserId(999L)).doesNotThrowAnyException();
    }

}
