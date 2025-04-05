package com.easy.store.backend.context.account_has_user.infrastructure.mappers;

import com.easy.store.backend.context.account.infrastructure.mappers.AccountMapper;
import com.easy.store.backend.context.account_has_user.application.dto.AccountHasUserCreateDto;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.infrastructure.persistence.AccountHasUserEntity;
import com.easy.store.backend.context.user.infrastructure.mappers.UserMapper;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class AccountHasUserCreateMapper extends BaseMapper<AccountHasUserEntity, AccountHasUser, AccountHasUserCreateDto> {

    private final AccountMapper accountMapper = new AccountMapper();
    private final UserMapper userMapper = new UserMapper();

    @Override
    public AccountHasUser entityToModel(AccountHasUserEntity entity) {
        return AccountHasUser.builder()
                .id(entity.getId())
                .accountId(accountMapper.entityToModel(entity.getAccountId()))
                .userId(userMapper.entityToModel(entity.getUserId()))
                .build();
    }

    @Override
    public AccountHasUserEntity modelToEntity(AccountHasUser model) {
        return AccountHasUserEntity.builder()
                .id(model.getId())
                .accountId(accountMapper.modelToEntity(model.getAccountId()))
                .userId(userMapper.modelToEntity(model.getUserId()))
                .build();
    }

    @Override
    public AccountHasUserCreateDto modelToDto(AccountHasUser model) {
        return AccountHasUserCreateDto.builder()
                .id(model.getId())
                .build();
    }

    @Override
    public AccountHasUser dtoToModel(AccountHasUserCreateDto dto) {
        return AccountHasUser.builder()
                .id(dto.getId())
                .build();
    }

}
