package com.easy.store.backend.context.payment_type.infrastructure.mappers;

import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeUpdateDTO;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class PaymentTypeUpdateMapper extends BaseMapper<PaymentTypeEntity, PaymentType, PaymentTypeUpdateDTO> {

    @Override
    public PaymentType entityToModel(PaymentTypeEntity entity) {
        return PaymentType.builder()
                .id(entity.getId())
                .name(entity.getName())
                .updateBy(entity.getUpdateBy())
                .state(entity.getState())
                .build();
    }

    @Override
    public PaymentTypeEntity modelToEntity(PaymentType model) {
        return PaymentTypeEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .updateBy(model.getUpdateBy())
                .state(model.getState())
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

}