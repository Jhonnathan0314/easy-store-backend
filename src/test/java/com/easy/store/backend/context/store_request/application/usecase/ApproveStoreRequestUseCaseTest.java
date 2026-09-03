package com.easy.store.backend.context.store_request.application.usecase;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.domain.port.AccountRepository;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.store.backend.context.account_has_user.domain.port.AccountHasUserRepository;
import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.domain.port.RoleRepository;
import com.easy.store.backend.context.store_request.domain.model.StoreRequest;
import com.easy.store.backend.context.store_request.domain.port.StoreRequestRepository;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.exceptions.InvalidActionException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApproveStoreRequestUseCaseTest {

    @Mock
    private StoreRequestRepository storeRequestRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountHasUserRepository accountHasUserRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private ApproveStoreRequestUseCase useCase;

    private StoreRequest pendingRequestFor(Long userId) {
        return StoreRequest.builder()
                .id(1L)
                .user(User.builder().id(userId).build())
                .storeName("Nueva Tienda")
                .storeDescription("Nueva descripción")
                .status(StoreRequest.STATUS_PENDING)
                .build();
    }

    private AccountHasUser accountHasUser(Long userId, Long accountId) {
        return AccountHasUser.builder()
                .id(AccountHasUserId.builder().userId(userId).accountId(accountId).build())
                .accountId(Account.builder().id(accountId).build())
                .userId(User.builder().id(userId).build())
                .build();
    }

    @Test
    void approve_requestNotPending_throwsInvalidActionException() {
        StoreRequest approved = pendingRequestFor(5L);
        approved.setStatus(StoreRequest.STATUS_APPROVED);
        when(storeRequestRepository.findById(1L)).thenReturn(Optional.of(approved));

        assertThatThrownBy(() -> useCase.approve(1L, 99L))
                .isInstanceOf(InvalidActionException.class);

        verifyNoInteractions(accountRepository, userRepository);
    }

    @Test
    void approve_requestNotFound_throwsNoResultsException() {
        when(storeRequestRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.approve(1L, 99L))
                .isInstanceOf(NoResultsException.class);
    }

    @Test
    void approve_updatesAccountAndUserRoleAndStoreRequest() throws Exception {
        Long userId = 5L;
        Long accountId = 10L;
        Long adminId = 99L;

        when(storeRequestRepository.findById(1L)).thenReturn(Optional.of(pendingRequestFor(userId)));
        when(accountHasUserRepository.findByUserId(userId)).thenReturn(List.of(accountHasUser(userId, accountId)));
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(
                Account.builder().id(accountId).name("Nombre por defecto").description("desc").build()
        ));
        Role ownerRole = Role.builder().id(2L).name("owner").build();
        when(roleRepository.findByName("owner")).thenReturn(Optional.of(ownerRole));
        when(userRepository.findById(userId)).thenReturn(Optional.of(
                User.builder().id(userId).role(Role.builder().id(1L).name("client").build()).build()
        ));
        when(storeRequestRepository.update(any(StoreRequest.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StoreRequest result = useCase.approve(1L, adminId);

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).update(accountCaptor.capture());
        assertThat(accountCaptor.getValue().getName()).isEqualTo("Nueva Tienda");
        assertThat(accountCaptor.getValue().getDescription()).isEqualTo("Nueva descripción");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).update(userCaptor.capture());
        assertThat(userCaptor.getValue().getRole()).isEqualTo(ownerRole);

        assertThat(result.getStatus()).isEqualTo(StoreRequest.STATUS_APPROVED);
        assertThat(result.getReviewedBy()).isEqualTo(adminId);
    }

}
