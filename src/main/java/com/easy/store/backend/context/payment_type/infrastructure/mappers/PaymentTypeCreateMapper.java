package com.easy.store.backend.context.payment_type.infrastructure.mappers;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.infrastructure.persistence.AccountEntity;
import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeCreateDTO;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentTypeCreateMapper implements Mapper<PaymentTypeEntity, PaymentType, PaymentTypeCreateDTO> {

    @Override
    public PaymentType entityToModel(PaymentTypeEntity entity) {
        return PaymentType.builder()
                .name(entity.getName())
                .createBy(entity.getCreateBy())
                .account(Account.builder()
                        .id(entity.getAccount().getId())
                        .build()
                )
                .build();
    }

    @Override
    public PaymentTypeEntity modelToEntity(PaymentType model) {
        return PaymentTypeEntity.builder()
                .name(model.getName())
                .createBy(model.getCreateBy())
                .account(AccountEntity.builder()
                        .id(model.getAccount().getId())
                        .build()
                )
                .build();
    }

    @Override
    public PaymentTypeCreateDTO modelToDto(PaymentType model) {
        return PaymentTypeCreateDTO.builder()
                .name(model.getName())
                .createBy(model.getCreateBy())
                .accountId(model.getAccount().getId())
                .build();
    }

    @Override
    public PaymentType dtoToModel(PaymentTypeCreateDTO dto) {
        return PaymentType.builder()
                .name(dto.getName())
                .createBy(dto.getCreateBy())
                .account(Account.builder()
                        .id(dto.getAccountId())
                        .build()
                )
                .build();
    }

    @Override
    public List<PaymentType> entitiesToModels(List<PaymentTypeEntity> entities) {
        return entities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentTypeEntity> modelsToEntities(List<PaymentType> models) {
        return models.stream()
                .map(this::modelToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentTypeCreateDTO> modelsToDtos(List<PaymentType> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentType> dtosToModels(List<PaymentTypeCreateDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}