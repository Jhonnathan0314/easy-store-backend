package com.easy.store.backend.context.payment_type.infrastructure.mappers;

import com.easy.store.backend.context.account.infrastructure.mappers.AccountMapper;
import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeDTO;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentTypeMapper implements Mapper<PaymentTypeEntity, PaymentType, PaymentTypeDTO> {

    private AccountMapper accountMapper = new AccountMapper();

    @Override
    public PaymentType entityToModel(PaymentTypeEntity entity) {
        return PaymentType.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createBy(entity.getCreateBy())
                .updateBy(entity.getUpdateBy())
                .creationDate(entity.getCreationDate())
                .updateDate(entity.getUpdateDate())
                .state(entity.getState())
                .account(accountMapper.entityToModel(entity.getAccount()))
                .build();
    }

    @Override
    public PaymentTypeEntity modelToEntity(PaymentType model) {
        return PaymentTypeEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .createBy(model.getCreateBy())
                .updateBy(model.getUpdateBy())
                .creationDate(model.getCreationDate())
                .updateDate(model.getUpdateDate())
                .state(model.getState())
                .account(accountMapper.modelToEntity(model.getAccount()))
                .build();
    }

    @Override
    public PaymentTypeDTO modelToDto(PaymentType model) {
        return PaymentTypeDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .createBy(model.getCreateBy())
                .updateBy(model.getUpdateBy())
                .account(accountMapper.modelToDto(model.getAccount()))
                .build();
    }

    @Override
    public PaymentType dtoToModel(PaymentTypeDTO dto) {
        return PaymentType.builder()
                .id(dto.getId())
                .name(dto.getName())
                .createBy(dto.getCreateBy())
                .updateBy(dto.getUpdateBy())
                .account(accountMapper.dtoToModel(dto.getAccount()))
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
    public List<PaymentTypeDTO> modelsToDtos(List<PaymentType> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentType> dtosToModels(List<PaymentTypeDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}