package com.easy.store.backend.context.purchase.application.service;

import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.domain.port.AccountHasUserRepository;
import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.ForbiddenActionException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Application-level authorization service for the Purchase and PurchaseHasProduct contexts.
 * <p>
 * It closes the IDOR gap left by {@code SecurityConfig}, which can only express role-based
 * rules (e.g. "only ADMIN/OWNER may call PUT/DELETE"). Ownership rules ("only the account/user
 * that owns this resource may read/write it") depend on request path/body values compared
 * against the currently authenticated user, so they are resolved here instead.
 * <p>
 * Roles:
 * <ul>
 *     <li><b>admin</b>: unrestricted access.</li>
 *     <li><b>owner</b>: access limited to accounts associated to them via {@code account_has_user}.</li>
 *     <li><b>client</b>/<b>ghost</b>: access limited to their own purchases (by user id).</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class PurchaseAuthorizationService {

    private static final String ADMIN = "admin";
    private static final String OWNER = "owner";

    private final AccountHasUserRepository accountHasUserRepository;
    private final CategoryRepository categoryRepository;
    private final PurchaseRepository purchaseRepository;

    /**
     * Rule 5: GET /api/v1/purchase (findAll) is admin-only.
     */
    public void authorizeFindAll() throws ForbiddenActionException {
        if (!isAdmin(currentUser())) throw forbidden();
    }

    /**
     * Rule 1: GET /api/v1/purchase/account/{accountId}.
     * admin: unrestricted. owner: only their own account. client/ghost: always forbidden.
     */
    public void authorizeFindByAccountId(Long accountId) throws ForbiddenActionException {
        User user = currentUser();
        if (isAdmin(user)) return;
        if (isOwner(user)) {
            if (ownerOwnsAccount(user.getId(), accountId)) return;
            throw forbidden();
        }
        throw forbidden();
    }

    /**
     * Rule 2: GET /api/v1/purchase/user/{userId}.
     * admin: unrestricted. owner: only if userId belongs to one of their accounts.
     * client/ghost: only their own userId.
     */
    public void authorizeFindByUserId(Long userId) throws ForbiddenActionException {
        User user = currentUser();
        if (isAdmin(user)) return;
        if (isOwner(user)) {
            if (ownerSharesAccountWithUser(user.getId(), userId)) return;
            throw forbidden();
        }
        if (Objects.equals(user.getId(), userId)) return;
        throw forbidden();
    }

    /**
     * Rule 4: GET /api/v1/purchase/category/{categoryId}.
     * admin: unrestricted. owner: only if the category belongs to one of their accounts.
     * client/ghost: always forbidden.
     */
    public void authorizeFindByCategoryId(Long categoryId) throws ForbiddenActionException {
        User user = currentUser();
        if (isAdmin(user)) return;
        if (isOwner(user)) {
            Long accountId = accountIdOfCategory(categoryId);
            if (accountId != null && ownerOwnsAccount(user.getId(), accountId)) return;
            throw forbidden();
        }
        throw forbidden();
    }

    /**
     * Rule 3 (and base rule reused by rule 7): GET /api/v1/purchase/{id} and any operation over a
     * purchase-has-product line that references a purchaseId.
     * admin: unrestricted. owner: only if the purchase's category belongs to one of their accounts.
     * client/ghost: only if the purchase belongs to them (purchase.user.id).
     * If the purchase does not exist, a {@link NoResultsException} is thrown (never a 403) so the
     * existence of the resource is not revealed to a caller who might not own it.
     *
     * @param purchaseId     the purchase id referenced by the request
     * @param notFoundMessage the message to use when the purchase does not exist, matching the
     *                        message the corresponding use case already uses, so the response the
     *                        caller sees is unchanged for the "not found" case.
     * @return the purchase, so callers that already need it can avoid a second lookup.
     */
    public Purchase authorizePurchaseAccess(Long purchaseId, String notFoundMessage) throws NoResultsException, ForbiddenActionException {
        Optional<Purchase> optPurchase = purchaseRepository.findById(purchaseId);
        if (optPurchase.isEmpty()) throw new NoResultsException(notFoundMessage);
        Purchase purchase = optPurchase.get();

        User user = currentUser();
        if (isAdmin(user)) return purchase;
        if (isOwner(user)) {
            Long accountId = accountIdOfCategory(purchase.getCategory().getId());
            if (accountId != null && ownerOwnsAccount(user.getId(), accountId)) return purchase;
            throw forbidden();
        }
        if (purchase.getUser() != null && Objects.equals(purchase.getUser().getId(), user.getId())) return purchase;
        throw forbidden();
    }

    /**
     * Rule 7 applied to a batch of purchase-has-product lines (addAll/removeAll): every distinct
     * purchaseId referenced by the batch must pass {@link #authorizePurchaseAccess}. If any line
     * references a purchase the caller does not own, the whole operation is rejected.
     */
    public void authorizePurchaseHasProductIds(List<PurchaseHasProductId> ids) throws NoResultsException, ForbiddenActionException {
        Set<Long> purchaseIds = ids.stream()
                .filter(Objects::nonNull)
                .map(PurchaseHasProductId::getPurchaseId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        for (Long purchaseId : purchaseIds) {
            authorizePurchaseAccess(purchaseId, ErrorMessages.NO_PURCHASE_RESULTS);
        }
    }

    /**
     * Rule 6: POST /api/v1/purchase (generate). The userId in the body must match the
     * authenticated user's id, unless the caller is admin.
     */
    public void authorizeGenerate(Long bodyUserId) throws ForbiddenActionException {
        User user = currentUser();
        if (isAdmin(user)) return;
        if (bodyUserId == null) return; // let use case validation (InvalidBodyException) handle malformed bodies
        if (Objects.equals(bodyUserId, user.getId())) return;
        throw forbidden();
    }

    private Long accountIdOfCategory(Long categoryId) {
        if (categoryId == null) return null;
        Optional<Category> optCategory = categoryRepository.findById(categoryId);
        return optCategory.map(Category::getAccount)
                .map(account -> account != null ? account.getId() : null)
                .orElse(null);
    }

    private boolean ownerOwnsAccount(Long ownerId, Long accountId) {
        if (accountId == null) return false;
        return accountHasUserRepository.findByUserId(ownerId).stream()
                .map(AccountHasUser::getAccountId)
                .filter(Objects::nonNull)
                .anyMatch(account -> accountId.equals(account.getId()));
    }

    private boolean ownerSharesAccountWithUser(Long ownerId, Long targetUserId) {
        Set<Long> ownerAccountIds = accountHasUserRepository.findByUserId(ownerId).stream()
                .map(AccountHasUser::getAccountId)
                .filter(Objects::nonNull)
                .map(account -> account.getId())
                .collect(Collectors.toSet());
        return accountHasUserRepository.findByUserId(targetUserId).stream()
                .map(AccountHasUser::getAccountId)
                .filter(Objects::nonNull)
                .map(account -> account.getId())
                .anyMatch(ownerAccountIds::contains);
    }

    private boolean isAdmin(User user) {
        return hasRole(user, ADMIN);
    }

    private boolean isOwner(User user) {
        return hasRole(user, OWNER);
    }

    private boolean hasRole(User user, String roleName) {
        return user.getRole() != null && roleName.equalsIgnoreCase(user.getRole().getName());
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
