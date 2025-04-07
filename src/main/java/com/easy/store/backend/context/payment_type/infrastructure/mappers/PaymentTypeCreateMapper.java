package com.easy.store.backend.context.payment_type.infrastructure.mappers;

import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeCreateDTO;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;


public class PaymentTypeCreateMapper extends BaseMapper<PaymentTypeEntity, PaymentType, PaymentTypeCreateDTO> {

    @Override
    public PaymentType entityToModel(PaymentTypeEntity entity) {
        return PaymentType.builder()
                .name(entity.getName())
                .createBy(entity.getCreateBy())
                .state(entity.getState())
                .build();
    }

    @Override
    public PaymentTypeEntity modelToEntity(PaymentType model) {
        return PaymentTypeEntity.builder()
                .name(model.getName())
                .createBy(model.getCreateBy())
                .state(model.getState())
                .build();
    }

    @Override
    public PaymentTypeCreateDTO modelToDto(PaymentType model) {
        return PaymentTypeCreateDTO.builder()
                .name(model.getName())
                .createBy(model.getCreateBy())
                .build();
    }

    @Override
    public PaymentType dtoToModel(PaymentTypeCreateDTO dto) {
        return PaymentType.builder()
                .name(dto.getName())
                .createBy(dto.getCreateBy())
                .build();
    }

}