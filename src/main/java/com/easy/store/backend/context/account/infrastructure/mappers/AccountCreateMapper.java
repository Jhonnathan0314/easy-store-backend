package com.easy.store.backend.context.account.infrastructure.mappers;

import com.easy.store.backend.context.account.application.dto.AccountCreateDto;
import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.infrastructure.persistence.AccountEntity;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class AccountCreateMapper implements Mapper<AccountEntity, Account, AccountCreateDto> {

    @Override
    public Account entityToModel(AccountEntity entity) {
        return Account.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .imageName(entity.getImageName())
                .build();
    }

    @Override
    public AccountEntity modelToEntity(Account model) {
        return AccountEntity.builder()
                .name(model.getName())
                .description(model.getDescription())
                .imageName(model.getImageName())
                .build();
    }

    @Override
    public AccountCreateDto modelToDto(Account model) {
        return AccountCreateDto.builder()
                .name(model.getName())
                .description(model.getDescription())
                .imageName(model.getImageName())
                .build();
    }

    @Override
    public Account dtoToModel(AccountCreateDto dto) {
        return Account.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .imageName(dto.getImageName())
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
    public List<AccountCreateDto> modelsToDtos(List<Account> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> dtosToModels(List<AccountCreateDto> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }
}
