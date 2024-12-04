package com.easy.store.backend.context.account.infrastructure.mappers;

import com.easy.store.backend.context.account.application.dto.AccountDto;
import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.infrastructure.persistence.AccountEntity;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper implements Mapper<AccountEntity, Account, AccountDto> {

    @Override
    public Account entityToModel(AccountEntity entity) {
        return Account.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .state(entity.getState())
                .build();
    }

    @Override
    public AccountEntity modelToEntity(Account model) {
        return AccountEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .state(model.getState())
                .build();
    }

    @Override
    public AccountDto modelToDto(Account model) {
        return AccountDto.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .state(model.getState())
                .build();
    }

    @Override
    public Account dtoToModel(AccountDto dto) {
        return Account.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .state(dto.getState())
                .build();
    }

    @Override
    public List<Account> entitiesToModels(List<AccountEntity> entities) {
        return entities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountEntity> modelsToEntities(List<Account> models) {
        return models.stream()
                .map(this::modelToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDto> modelsToDtos(List<Account> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> dtosToModels(List<AccountDto> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }
}
