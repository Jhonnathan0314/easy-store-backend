package com.easy.store.backend.context.account_has_user.infrastructure.adapter;

import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.store.backend.context.account_has_user.domain.port.AccountHasUserRepository;
import com.easy.store.backend.context.account_has_user.infrastructure.mappers.AccountHasUserCreateMapper;
import com.easy.store.backend.context.account_has_user.infrastructure.mappers.AccountHasUserMapper;
import com.easy.store.backend.context.account_has_user.infrastructure.persistence.AccountHasUserEntity;
import com.easy.store.backend.context.account_has_user.infrastructure.persistence.AccountHasUserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Repository
@RequiredArgsConstructor
public class AccountHasUserJpaAdapter implements AccountHasUserRepository {

    private final Logger logger = Logger.getLogger(AccountHasUserJpaAdapter.class.getName());

    private final AccountHasUserJpaRepository accountHasUserJpaRepository;

    private final AccountHasUserMapper accountHasUserMapper = new AccountHasUserMapper();
    private final AccountHasUserCreateMapper accountHasUserCreateMapper = new AccountHasUserCreateMapper();

    @Override
    public List<AccountHasUser> findByAccountId(Long idAccount) {
        List<AccountHasUserEntity> entities = accountHasUserJpaRepository.findById_AccountId(idAccount);
        return accountHasUserMapper.entitiesToModels(entities);
    }

    @Override
    public List<AccountHasUser> findByUserId(Long userId) {
        List<AccountHasUserEntity> entities = accountHasUserJpaRepository.findById_UserId(userId);
        return accountHasUserMapper.entitiesToModels(entities);
    }

    @Override
    public Optional<AccountHasUser> findById(AccountHasUserId accountHasUserId) {
        Optional<AccountHasUserEntity> entities = accountHasUserJpaRepository
                .findById_AccountIdAndId_UserId(accountHasUserId.getAccountId(), accountHasUserId.getUserId());
        return entities.map(accountHasUserMapper::entityToModel);
    }

    @Override
    public List<AccountHasUser> findByState(String state) {
        List<AccountHasUserEntity> entities = accountHasUserJpaRepository.findByState(state);
        return accountHasUserMapper.entitiesToModels(entities);
    }

    @Override
    public AccountHasUser create(AccountHasUser accountHasUser) {
        logger.info("recibo model para jpa: " + accountHasUser.toString());
        logger.info("transformo a entity para jpa: " + accountHasUserCreateMapper
                .modelToEntity(accountHasUser).toString());
        AccountHasUserEntity entity = accountHasUserJpaRepository
                .save(accountHasUserCreateMapper.modelToEntity(accountHasUser));
        return accountHasUserMapper.entityToModel(entity);
    }

    @Override
    public void deleteById(AccountHasUserId accountHasUserId) {
        accountHasUserJpaRepository
                .deleteById_AccountIdAndId_UserId(accountHasUserId.getAccountId(), accountHasUserId.getUserId());
    }
}
