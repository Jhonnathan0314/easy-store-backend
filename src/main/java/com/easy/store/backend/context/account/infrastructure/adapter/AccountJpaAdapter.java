package com.easy.store.backend.context.account.infrastructure.adapter;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.domain.port.AccountRepository;
import com.easy.store.backend.context.account.infrastructure.mappers.AccountCreateMapper;
import com.easy.store.backend.context.account.infrastructure.mappers.AccountMapper;
import com.easy.store.backend.context.account.infrastructure.mappers.AccountUpdateMapper;
import com.easy.store.backend.context.account.infrastructure.persistence.AccountEntity;
import com.easy.store.backend.context.account.infrastructure.persistence.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountJpaAdapter implements AccountRepository {

    private final AccountJpaRepository accountJpaRepository;

    private final AccountMapper accountMapper = new AccountMapper();
    private final AccountCreateMapper accountCreateMapper = new AccountCreateMapper();
    private final AccountUpdateMapper accountUpdateMapper = new AccountUpdateMapper();

    @Override
    public List<Account> findAll() {
        List<AccountEntity> accounts = accountJpaRepository.findAll();
        return accountMapper.entitiesToModels(accounts);
    }

    @Override
    public Optional<Account> findById(Long id) {
        Optional<AccountEntity> accountEntity = accountJpaRepository.findById(id);
        return accountEntity.map(accountMapper::entityToModel);
    }

    @Override
    public Account create(Account account) {
        AccountEntity accountEntity = accountJpaRepository.save(accountCreateMapper.modelToEntity(account));
        return accountMapper.entityToModel(accountEntity);
    }

    @Override
    public Account update(Account account) {
        AccountEntity accountEntity = accountJpaRepository.save(accountUpdateMapper.modelToEntity(account));
        return accountMapper.entityToModel(accountEntity);
    }

    @Override
    public void deleteById(Long id) {
        accountJpaRepository.deleteById(id);
    }
}
