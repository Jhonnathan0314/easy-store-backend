package com.easy.store.backend.context.account_has_user.infrastructure.mappers;

import com.easy.store.backend.context.account.infrastructure.mappers.AccountMapper;
import com.easy.store.backend.context.account_has_user.application.dto.AccountHasUserResponseDto;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.infrastructure.persistence.AccountHasUserEntity;
import com.easy.store.backend.context.user.infrastructure.mappers.UserResponseMapper;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class AccountHasUserResponseMapper extends BaseMapper<AccountHasUserEntity, AccountHasUser, AccountHasUserResponseDto> {

    private final AccountMapper accountMapper = new AccountMapper();
    private final UserResponseMapper userMapper = new UserResponseMapper();

    @Override
    public AccountHasUser entityToModel(AccountHasUserEntity entity) {
        return AccountHasUser.builder()
                .id(entity.getId())
                .accountId(accountMapper.entityToModel(entity.getAccountId()))
                .userId(userMapper.entityToModel(entity.getUserId()))
                .state(entity.getState())
                .build();
    }

    @Override
    public AccountHasUserEntity modelToEntity(AccountHasUser model) {
        return AccountHasUserEntity.builder()
                .id(model.getId())
                .accountId(accountMapper.modelToEntity(model.getAccountId()))
                .userId(userMapper.modelToEntity(model.getUserId()))
                .state(model.getState())
                .build();
    }

    @Override
    public AccountHasUserResponseDto modelToDto(AccountHasUser model) {
        return AccountHasUserResponseDto.builder()
                .id(model.getId())
                .account(accountMapper.modelToDto(model.getAccountId()))
                .user(userMapper.modelToDto(model.getUserId()))
                .state(model.getState())
                .build();
    }

    @Override
    public AccountHasUser dtoToModel(AccountHasUserResponseDto dto) {
        return AccountHasUser.builder()
                .id(dto.getId())
                .accountId(accountMapper.dtoToModel(dto.getAccount()))
                .userId(userMapper.dtoToModel(dto.getUser()))
                .state(dto.getState())
                .build();
    }

}
