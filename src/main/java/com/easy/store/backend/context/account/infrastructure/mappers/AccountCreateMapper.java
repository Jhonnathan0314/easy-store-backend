package com.easy.store.backend.context.account.infrastructure.mappers;

import com.easy.store.backend.context.account.application.dto.AccountCreateDto;
import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.infrastructure.persistence.AccountEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class AccountCreateMapper extends BaseMapper<AccountEntity, Account, AccountCreateDto> {

    @Override
    public Account entityToModel(AccountEntity entity) {
        return Account.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .imageName(entity.getImageName())
                .state(entity.getState())
                .build();
    }

    @Override
    public AccountEntity modelToEntity(Account model) {
        return AccountEntity.builder()
                .name(model.getName())
                .description(model.getDescription())
                .imageName(model.getImageName())
                .state(model.getState())
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

}
