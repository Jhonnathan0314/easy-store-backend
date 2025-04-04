package com.easy.store.backend.context.payment_type.infrastructure.mappers;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.infrastructure.persistence.AccountEntity;
import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeUpdateDTO;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentTypeUpdateMapper extends BaseMapper<PaymentTypeEntity, PaymentType, PaymentTypeUpdateDTO> {

    @Override
    public PaymentType entityToModel(PaymentTypeEntity entity) {
        return PaymentType.builder()
                .id(entity.getId())
                .name(entity.getName())
                .updateBy(entity.getUpdateBy())
                .account(Account.builder()
                        .id(entity.getAccount().getId())
                        .build()
                )
                .state(entity.getState())
                .build();
    }

    @Override
    public PaymentTypeEntity modelToEntity(PaymentType model) {
        return PaymentTypeEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .updateBy(model.getUpdateBy())
                .account(AccountEntity.builder()
                        .id(model.getAccount().getId())
                        .build()
                )
                .state(model.getState())
                .build();
    }

    @Override
    public PaymentTypeUpdateDTO modelToDto(PaymentType model) {
        return PaymentTypeUpdateDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .updateBy(model.getUpdateBy())
                .accountId(model.getAccount().getId())
                .build();
    }

    @Override
    public PaymentType dtoToModel(PaymentTypeUpdateDTO dto) {
        return PaymentType.builder()
                .id(dto.getId())
                .name(dto.getName())
                .updateBy(dto.getUpdateBy())
                .account(Account.builder()
                        .id(dto.getAccountId())
                        .build()
                )
                .build();
    }

}