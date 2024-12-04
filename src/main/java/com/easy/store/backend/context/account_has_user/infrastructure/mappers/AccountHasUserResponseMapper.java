package com.easy.store.backend.context.account_has_user.infrastructure.mappers;

import com.easy.store.backend.context.account.infrastructure.mappers.AccountMapper;
import com.easy.store.backend.context.account_has_user.application.dto.AccountHasUserResponseDto;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.infrastructure.persistence.AccountHasUserEntity;
import com.easy.store.backend.context.user.infrastructure.mappers.UserResponseMapper;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class AccountHasUserResponseMapper implements Mapper<AccountHasUserEntity, AccountHasUser, AccountHasUserResponseDto> {

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
    public List<AccountHasUserResponseDto> modelsToDtos(List<AccountHasUser> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountHasUser> dtosToModels(List<AccountHasUserResponseDto> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }
}
