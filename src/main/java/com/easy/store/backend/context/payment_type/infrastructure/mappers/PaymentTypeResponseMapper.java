package com.easy.store.backend.context.payment_type.infrastructure.mappers;

import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeResponseDTO;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;


public class PaymentTypeResponseMapper extends BaseMapper<PaymentTypeEntity, PaymentType, PaymentTypeResponseDTO> {

    @Override
    public PaymentType entityToModel(PaymentTypeEntity entity) {
        return PaymentType.builder()
                .id(entity.getId())
                .name(entity.getName())
                .state(entity.getState())
                .build();
    }

    @Override
    public PaymentTypeEntity modelToEntity(PaymentType model) {
        return PaymentTypeEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .state(model.getState())
                .build();
    }

    @Override
    public PaymentTypeResponseDTO modelToDto(PaymentType model) {
        return PaymentTypeResponseDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .state(model.getState())
                .build();
    }

    @Override
    public PaymentType dtoToModel(PaymentTypeResponseDTO dto) {
        return PaymentType.builder()
                .id(dto.getId())
                .name(dto.getName())
                .state(dto.getState())
                .build();
    }

}