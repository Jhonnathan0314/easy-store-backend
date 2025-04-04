package com.easy.store.backend.context.account.infrastructure.mappers;

import com.easy.store.backend.context.account.application.dto.AccountDto;
import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.infrastructure.persistence.AccountEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class AccountMapper extends BaseMapper<AccountEntity, Account, AccountDto> {

    @Override
    public Account entityToModel(AccountEntity entity) {
        return Account.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .state(entity.getState())
                .imageName(entity.getImageName())
                .build();
    }

    @Override
    public AccountEntity modelToEntity(Account model) {
        return AccountEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .state(model.getState())
                .imageName(model.getImageName())
                .build();
    }

    @Override
    public AccountDto modelToDto(Account model) {
        return AccountDto.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .state(model.getState())
                .imageName(model.getImageName())
                .build();
    }

    @Override
    public Account dtoToModel(AccountDto dto) {
        return Account.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .state(dto.getState())
                .imageName(dto.getImageName())
                .build();
    }

}
