package com.sophie.store.backend.context.payment_type.infrastructure.mappers;

import com.sophie.store.backend.context.payment_type.application.dto.PaymentTypeUpdateDTO;
import com.sophie.store.backend.context.payment_type.domain.model.PaymentType;
import com.sophie.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.sophie.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentTypeUpdateMapper implements Mapper<PaymentTypeEntity, PaymentType, PaymentTypeUpdateDTO> {

    @Override
    public PaymentType entityToModel(PaymentTypeEntity entity) {
        return PaymentType.builder()
                .id(entity.getId())
                .name(entity.getName())
                .updateBy(entity.getUpdateBy())
                .build();
    }

    @Override
    public PaymentTypeEntity modelToEntity(PaymentType model) {
        return PaymentTypeEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .updateBy(model.getUpdateBy())
                .build();
    }

    @Override
    public PaymentTypeUpdateDTO modelToDto(PaymentType model) {
        return PaymentTypeUpdateDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .updateBy(model.getUpdateBy())
                .build();
    }

    @Override
    public PaymentType dtoToModel(PaymentTypeUpdateDTO dto) {
        return PaymentType.builder()
                .id(dto.getId())
                .name(dto.getName())
                .updateBy(dto.getUpdateBy())
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
    public List<PaymentTypeUpdateDTO> modelsToDtos(List<PaymentType> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentType> dtosToModels(List<PaymentTypeUpdateDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}