package com.easy.store.backend.context.account_has_user.infrastructure.persistence;

import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountHasUserJpaRepository extends JpaRepository<AccountHasUserEntity, AccountHasUserId> {

    List<AccountHasUserEntity> findById_AccountId(Long accountId);
    List<AccountHasUserEntity> findById_UserId(Long userId);
    List<AccountHasUserEntity> findByState(String state);
    Optional<AccountHasUserEntity> findById_AccountIdAndId_UserId(Long accountId, Long userId);
    void deleteById_AccountIdAndId_UserId(Long accountId, Long userId);

}
