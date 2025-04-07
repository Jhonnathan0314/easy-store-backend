package com.easy.store.backend.context.payment_type.infrastructure.mappers;

import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeDTO;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;


public class PaymentTypeMapper extends BaseMapper<PaymentTypeEntity, PaymentType, PaymentTypeDTO> {

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
                .build();
    }

    @Override
    public PaymentTypeDTO modelToDto(PaymentType model) {
        return PaymentTypeDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .createBy(model.getCreateBy())
                .updateBy(model.getUpdateBy())
                .build();
    }

    @Override
    public PaymentType dtoToModel(PaymentTypeDTO dto) {
        return PaymentType.builder()
                .id(dto.getId())
                .name(dto.getName())
                .createBy(dto.getCreateBy())
                .updateBy(dto.getUpdateBy())
                .build();
    }

}