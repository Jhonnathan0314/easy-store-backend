package com.easy.store.backend.context.store_request.application.usecase;

import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.store_request.domain.model.StoreRequest;
import com.easy.store.backend.context.store_request.domain.port.StoreRequestRepository;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.exceptions.InvalidActionException;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateStoreRequestUseCaseTest {

    @Mock
    private StoreRequestRepository storeRequestRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateStoreRequestUseCase useCase;

    private User userWithRole(Long id, String roleName) {
        return User.builder()
                .id(id)
                .username("user-" + id)
                .role(Role.builder().id(1L).name(roleName).build())
                .build();
    }

    private StoreRequest requestFor(Long userId) {
        return StoreRequest.builder()
                .user(User.builder().id(userId).build())
                .storeName("Mi Tienda")
                .storeDescription("Descripción de mi tienda")
                .build();
    }

    @Test
    void create_userIsNotClient_throwsInvalidActionException() {
        when(userRepository.findById(5L)).thenReturn(Optional.of(userWithRole(5L, "owner")));

        assertThatThrownBy(() -> useCase.create(requestFor(5L)))
                .isInstanceOf(InvalidActionException.class);
    }

    @Test
    void create_userAlreadyHasPendingRequest_throwsInvalidActionException() {
        when(userRepository.findById(5L)).thenReturn(Optional.of(userWithRole(5L, "client")));
        when(storeRequestRepository.findLatestPendingByUserId(5L))
                .thenReturn(Optional.of(StoreRequest.builder().id(1L).status(StoreRequest.STATUS_PENDING).build()));

        assertThatThrownBy(() -> useCase.create(requestFor(5L)))
                .isInstanceOf(InvalidActionException.class);
    }

    @Test
    void create_invalidBody_throwsInvalidBodyException() {
        StoreRequest invalid = StoreRequest.builder()
                .user(User.builder().id(5L).build())
                .storeName("")
                .storeDescription("")
                .build();

        assertThatThrownBy(() -> useCase.create(invalid))
                .isInstanceOf(InvalidBodyException.class);
    }

    @Test
    void create_userNotFound_throwsNoResultsException() {
        when(userRepository.findById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.create(requestFor(5L)))
                .isInstanceOf(NoResultsException.class);
    }

    @Test
    void create_clientWithoutPendingRequest_createsSuccessfully() throws Exception {
        User user = userWithRole(5L, "client");
        when(userRepository.findById(5L)).thenReturn(Optional.of(user));
        when(storeRequestRepository.findLatestPendingByUserId(5L)).thenReturn(Optional.empty());
        when(storeRequestRepository.create(any(StoreRequest.class))).thenAnswer(invocation -> {
            StoreRequest arg = invocation.getArgument(0);
            arg.setId(1L);
            return arg;
        });

        StoreRequest result = useCase.create(requestFor(5L));

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStatus()).isEqualTo(StoreRequest.STATUS_PENDING);
        assertThat(result.getUser()).isEqualTo(user);
    }

}
