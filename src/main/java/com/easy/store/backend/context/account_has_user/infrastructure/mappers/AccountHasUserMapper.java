package com.easy.store.backend.context.account_has_user.infrastructure.mappers;

import com.easy.store.backend.context.account.infrastructure.mappers.AccountMapper;
import com.easy.store.backend.context.account_has_user.application.dto.AccountHasUserDto;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.infrastructure.persistence.AccountHasUserEntity;
import com.easy.store.backend.context.user.infrastructure.mappers.UserMapper;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class AccountHasUserMapper implements Mapper<AccountHasUserEntity, AccountHasUser, AccountHasUserDto> {

    private final AccountMapper accountMapper = new AccountMapper();
    private final UserMapper userMapper = new UserMapper();

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
    public AccountHasUserDto modelToDto(AccountHasUser model) {
        return AccountHasUserDto.builder()
                .id(model.getId())
                .account(accountMapper.modelToDto(model.getAccountId()))
                .user(userMapper.modelToDto(model.getUserId()))
                .state(model.getState())
                .build();
    }

    @Override
    public AccountHasUser dtoToModel(AccountHasUserDto dto) {
        return AccountHasUser.builder()
                .id(dto.getId())
                .accountId(accountMapper.dtoToModel(dto.getAccount()))
                .userId(userMapper.dtoToModel(dto.getUser()))
                .state(dto.getState())
                .build();
    }

    @Override
    public List<AccountHasUser> entitiesToModels(List<AccountHasUserEntity> entities) {
        return entities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountHasUserEntity> modelsToEntities(List<AccountHasUser> models) {
        return models.stream()
                .map(this::modelToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountHasUserDto> modelsToDtos(List<AccountHasUser> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountHasUser> dtosToModels(List<AccountHasUserDto> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }
}