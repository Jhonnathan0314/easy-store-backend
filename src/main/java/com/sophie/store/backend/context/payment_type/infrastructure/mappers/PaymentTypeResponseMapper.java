package com.sophie.store.backend.context.payment_type.infrastructure.mappers;

import com.sophie.store.backend.context.payment_type.application.dto.PaymentTypeResponseDTO;
import com.sophie.store.backend.context.payment_type.domain.model.PaymentType;
import com.sophie.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.sophie.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentTypeResponseMapper implements Mapper<PaymentTypeEntity, PaymentType, PaymentTypeResponseDTO> {

    @Override
    public PaymentType entityToModel(PaymentTypeEntity entity) {
        return PaymentType.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    public PaymentTypeEntity modelToEntity(PaymentType model) {
        return PaymentTypeEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
    }

    @Override
    public PaymentTypeResponseDTO modelToDto(PaymentType model) {
        return PaymentTypeResponseDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
    }

    @Override
    public PaymentType dtoToModel(PaymentTypeResponseDTO dto) {
        return PaymentType.builder()
                .id(dto.getId())
                .name(dto.getName())
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
    public List<PaymentTypeResponseDTO> modelsToDtos(List<PaymentType> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentType> dtosToModels(List<PaymentTypeResponseDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}