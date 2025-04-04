package com.easy.store.backend.context.account.infrastructure.mappers;

import com.easy.store.backend.context.account.application.dto.AccountUpdateDto;
import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.infrastructure.persistence.AccountEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class AccountUpdateMapper extends BaseMapper<AccountEntity, Account, AccountUpdateDto> {

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
    public AccountUpdateDto modelToDto(Account model) {
        return AccountUpdateDto.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .state(model.getState())
                .imageName(model.getImageName())
                .build();
    }

    @Override
    public Account dtoToModel(AccountUpdateDto dto) {
        return Account.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .state(dto.getState())
                .imageName(dto.getImageName())
                .build();
    }

}
