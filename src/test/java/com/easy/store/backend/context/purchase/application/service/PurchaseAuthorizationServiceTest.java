package com.easy.store.backend.context.purchase.application.service;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.store.backend.context.account_has_user.domain.port.AccountHasUserRepository;
import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.utils.exceptions.ForbiddenActionException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

/**
 * Exploration + fix-checking + preservation tests for {@link PurchaseAuthorizationService}.
 * <p>
 * These tests exercise the service directly (it is the unit that closes the IDOR gap). The
 * corresponding controller-level exploration/preservation tests live in
 * {@code PurchaseControllerTest} and {@code PurchaseHasProductControllerTest}.
 */
@ExtendWith(MockitoExtension.class)
class PurchaseAuthorizationServiceTest {

    @Mock
    private AccountHasUserRepository accountHasUserRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private PurchaseAuthorizationService service;

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

    private AccountHasUser accountHasUser(Long userId, Long accountId) {
        return AccountHasUser.builder()
                .id(AccountHasUserId.builder().userId(userId).accountId(accountId).build())
                .accountId(Account.builder().id(accountId).build())
                .userId(User.builder().id(userId).build())
                .build();
    }

    private Category categoryOfAccount(Long categoryId, Long accountId) {
        return Category.builder()
                .id(categoryId)
                .account(Account.builder().id(accountId).build())
                .build();
    }

    private Purchase purchaseOf(Long purchaseId, Long userId, Long categoryId) {
        return Purchase.builder()
                .id(purchaseId)
                .user(User.builder().id(userId).build())
                .category(Category.builder().id(categoryId).build())
                .build();
    }

    // ---------------------------------------------------------------------
    // Property 1 (Bug Condition / Fix Checking): non-owners must get 403
    // ---------------------------------------------------------------------

    @Test
    void findAll_nonAdmin_isForbidden() {
        authenticateAs(1L, "owner");
        assertThatThrownBy(() -> service.authorizeFindAll())
                .isInstanceOf(ForbiddenActionException.class);
    }

    @Test
    void findByAccountId_ownerOfDifferentAccount_isForbidden() {
        authenticateAs(1L, "owner");
        when(accountHasUserRepository.findByUserId(1L)).thenReturn(List.of(accountHasUser(1L, 10L)));

        assertThatThrownBy(() -> service.authorizeFindByAccountId(20L))
                .isInstanceOf(ForbiddenActionException.class);
    }

    @Test
    void findByAccountId_clientRole_isAlwaysForbidden() {
        authenticateAs(5L, "client");
        assertThatThrownBy(() -> service.authorizeFindByAccountId(10L))
                .isInstanceOf(ForbiddenActionException.class);
    }

    @Test
    void findByAccountId_ghostRole_isAlwaysForbidden() {
        authenticateAs(5L, "ghost");
        assertThatThrownBy(() -> service.authorizeFindByAccountId(10L))
                .isInstanceOf(ForbiddenActionException.class);
    }

    @Test
    void findByUserId_clientRequestingOtherUser_isForbidden() {
        authenticateAs(5L, "client");
        assertThatThrownBy(() -> service.authorizeFindByUserId(9L))
                .isInstanceOf(ForbiddenActionException.class);
    }

    @Test
    void findByUserId_ownerWithoutSharedAccount_isForbidden() {
        authenticateAs(1L, "owner");
        when(accountHasUserRepository.findByUserId(1L)).thenReturn(List.of(accountHasUser(1L, 10L)));
        when(accountHasUserRepository.findByUserId(9L)).thenReturn(List.of(accountHasUser(9L, 20L)));

        assertThatThrownBy(() -> service.authorizeFindByUserId(9L))
                .isInstanceOf(ForbiddenActionException.class);
    }

    @Test
    void findByCategoryId_ownerOfDifferentAccount_isForbidden() {
        authenticateAs(1L, "owner");
        when(categoryRepository.findById(100L)).thenReturn(Optional.of(categoryOfAccount(100L, 20L)));
        when(accountHasUserRepository.findByUserId(1L)).thenReturn(List.of(accountHasUser(1L, 10L)));

        assertThatThrownBy(() -> service.authorizeFindByCategoryId(100L))
                .isInstanceOf(ForbiddenActionException.class);
    }

    @Test
    void findByCategoryId_clientRole_isAlwaysForbidden() {
        authenticateAs(5L, "client");
        assertThatThrownBy(() -> service.authorizeFindByCategoryId(100L))
                .isInstanceOf(ForbiddenActionException.class);
    }

    @Test
    void purchaseAccess_clientOfDifferentUser_isForbidden() {
        authenticateAs(5L, "client");
        when(purchaseRepository.findById(12L)).thenReturn(Optional.of(purchaseOf(12L, 9L, 100L)));

        assertThatThrownBy(() -> service.authorizePurchaseAccess(12L, "not found"))
                .isInstanceOf(ForbiddenActionException.class);
    }

    @Test
    void purchaseAccess_ownerOfDifferentAccount_isForbidden() {
        authenticateAs(1L, "owner");
        when(purchaseRepository.findById(12L)).thenReturn(Optional.of(purchaseOf(12L, 9L, 100L)));
        when(categoryRepository.findById(100L)).thenReturn(Optional.of(categoryOfAccount(100L, 20L)));
        when(accountHasUserRepository.findByUserId(1L)).thenReturn(List.of(accountHasUser(1L, 10L)));

        assertThatThrownBy(() -> service.authorizePurchaseAccess(12L, "not found"))
                .isInstanceOf(ForbiddenActionException.class);
    }

    @Test
    void purchaseHasProductIds_batchWithOneForeignPurchase_rejectsWholeBatch() {
        authenticateAs(5L, "client");
        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchaseOf(1L, 5L, 100L)));
        when(purchaseRepository.findById(2L)).thenReturn(Optional.of(purchaseOf(2L, 9L, 200L)));

        List<PurchaseHasProductId> ids = List.of(
                PurchaseHasProductId.builder().purchaseId(1L).productId(1L).build(),
                PurchaseHasProductId.builder().purchaseId(2L).productId(2L).build()
        );

        assertThatThrownBy(() -> service.authorizePurchaseHasProductIds(ids))
                .isInstanceOf(ForbiddenActionException.class);
    }

    @Test
    void generate_nonAdminWithForeignUserId_isForbidden() {
        authenticateAs(5L, "client");
        assertThatThrownBy(() -> service.authorizeGenerate(9L))
                .isInstanceOf(ForbiddenActionException.class);
    }

    // ---------------------------------------------------------------------
    // Preservation: existence checks are unaffected (404 semantics preserved)
    // ---------------------------------------------------------------------

    @Test
    void purchaseAccess_nonExistentPurchase_throwsNoResultsNotForbidden() {
        authenticateAs(5L, "client");
        when(purchaseRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.authorizePurchaseAccess(999L, "No se encontraron resultados."))
                .isInstanceOf(NoResultsException.class);
    }

    // ---------------------------------------------------------------------
    // Property 2 (Preservation): legitimate access must not be blocked
    // ---------------------------------------------------------------------

    @Test
    void findAll_admin_isAllowed() {
        authenticateAs(1L, "admin");
        assertThatCode(() -> service.authorizeFindAll()).doesNotThrowAnyException();
    }

    @Test
    void findByAccountId_admin_isAllowedForAnyAccount() {
        authenticateAs(1L, "admin");
        assertThatCode(() -> service.authorizeFindByAccountId(999L)).doesNotThrowAnyException();
    }

    @Test
    void findByAccountId_ownerOfOwnAccount_isAllowed() {
        authenticateAs(1L, "owner");
        when(accountHasUserRepository.findByUserId(1L)).thenReturn(List.of(accountHasUser(1L, 10L)));

        assertThatCode(() -> service.authorizeFindByAccountId(10L)).doesNotThrowAnyException();
    }

    @Test
    void findByUserId_ownerSharingAccountWithTarget_isAllowed() {
        authenticateAs(1L, "owner");
        when(accountHasUserRepository.findByUserId(1L)).thenReturn(List.of(accountHasUser(1L, 10L)));
        when(accountHasUserRepository.findByUserId(9L)).thenReturn(List.of(accountHasUser(9L, 10L)));

        assertThatCode(() -> service.authorizeFindByUserId(9L)).doesNotThrowAnyException();
    }

    @Test
    void findByUserId_clientRequestingOwnUserId_isAllowed() {
        authenticateAs(5L, "client");
        assertThatCode(() -> service.authorizeFindByUserId(5L)).doesNotThrowAnyException();
    }

    @Test
    void findByUserId_ghostRequestingOwnUserId_isAllowed() {
        authenticateAs(5L, "ghost");
        assertThatCode(() -> service.authorizeFindByUserId(5L)).doesNotThrowAnyException();
    }

    @Test
    void findByCategoryId_ownerOfOwnAccount_isAllowed() {
        authenticateAs(1L, "owner");
        when(categoryRepository.findById(100L)).thenReturn(Optional.of(categoryOfAccount(100L, 10L)));
        when(accountHasUserRepository.findByUserId(1L)).thenReturn(List.of(accountHasUser(1L, 10L)));

        assertThatCode(() -> service.authorizeFindByCategoryId(100L)).doesNotThrowAnyException();
    }

    @Test
    void purchaseAccess_clientOfOwnPurchase_isAllowed() {
        authenticateAs(5L, "client");
        when(purchaseRepository.findById(12L)).thenReturn(Optional.of(purchaseOf(12L, 5L, 100L)));

        assertThatCode(() -> service.authorizePurchaseAccess(12L, "not found")).doesNotThrowAnyException();
    }

    @Test
    void purchaseAccess_ghostOfOwnPurchase_isAllowed() {
        authenticateAs(5L, "ghost");
        when(purchaseRepository.findById(12L)).thenReturn(Optional.of(purchaseOf(12L, 5L, 100L)));

        assertThatCode(() -> service.authorizePurchaseAccess(12L, "not found")).doesNotThrowAnyException();
    }

    @Test
    void purchaseAccess_ownerOfOwnAccount_isAllowed() {
        authenticateAs(1L, "owner");
        when(purchaseRepository.findById(12L)).thenReturn(Optional.of(purchaseOf(12L, 9L, 100L)));
        when(categoryRepository.findById(100L)).thenReturn(Optional.of(categoryOfAccount(100L, 10L)));
        when(accountHasUserRepository.findByUserId(1L)).thenReturn(List.of(accountHasUser(1L, 10L)));

        assertThatCode(() -> service.authorizePurchaseAccess(12L, "not found")).doesNotThrowAnyException();
    }

    @Test
    void purchaseHasProductIds_batchOfOwnPurchases_isAllowed() {
        authenticateAs(5L, "client");
        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchaseOf(1L, 5L, 100L)));
        when(purchaseRepository.findById(2L)).thenReturn(Optional.of(purchaseOf(2L, 5L, 200L)));

        List<PurchaseHasProductId> ids = List.of(
                PurchaseHasProductId.builder().purchaseId(1L).productId(1L).build(),
                PurchaseHasProductId.builder().purchaseId(2L).productId(2L).build()
        );

        assertThatCode(() -> service.authorizePurchaseHasProductIds(ids)).doesNotThrowAnyException();
    }

    @Test
    void generate_adminWithForeignUserId_isAllowed() {
        authenticateAs(1L, "admin");
        assertThatCode(() -> service.authorizeGenerate(9L)).doesNotThrowAnyException();
    }

    @Test
    void generate_clientWithOwnUserId_isAllowed() {
        authenticateAs(5L, "client");
        assertThatCode(() -> service.authorizeGenerate(5L)).doesNotThrowAnyException();
    }

    @Test
    void generate_ghostWithOwnUserId_isAllowed() {
        authenticateAs(5L, "ghost");
        assertThatCode(() -> service.authorizeGenerate(5L)).doesNotThrowAnyException();
    }

    @Test
    void authorizePurchaseAccess_returnsResolvedPurchase() throws Exception {
        authenticateAs(1L, "admin");
        Purchase purchase = purchaseOf(12L, 9L, 100L);
        when(purchaseRepository.findById(12L)).thenReturn(Optional.of(purchase));

        Purchase result = service.authorizePurchaseAccess(12L, "not found");

        assertThat(result).isSameAs(purchase);
    }
}
