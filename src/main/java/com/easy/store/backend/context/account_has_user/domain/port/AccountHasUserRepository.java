package com.easy.store.backend.context.account_has_user.domain.port;

import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUserId;

import java.util.List;
import java.util.Optional;

public interface AccountHasUserRepository {

    List<AccountHasUser> findByAccountId(Long accountId);
    List<AccountHasUser> findByUserId(Long userId);
    Optional<AccountHasUser> findById(AccountHasUserId accountHasUserId);
    List<AccountHasUser> findByState(String state);
    AccountHasUser create(AccountHasUser accountHasUser);
    void deleteById(AccountHasUserId accountHasUserId);

}
