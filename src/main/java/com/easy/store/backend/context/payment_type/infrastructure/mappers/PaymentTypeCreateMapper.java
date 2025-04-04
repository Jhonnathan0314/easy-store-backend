package com.easy.store.backend.context.payment_type.infrastructure.mappers;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.infrastructure.persistence.AccountEntity;
import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeCreateDTO;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentTypeCreateMapper extends BaseMapper<PaymentTypeEntity, PaymentType, PaymentTypeCreateDTO> {

    @Override
    public PaymentType entityToModel(PaymentTypeEntity entity) {
        return PaymentType.builder()
                .name(entity.getName())
                .createBy(entity.getCreateBy())
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
                .name(model.getName())
                .createBy(model.getCreateBy())
                .account(AccountEntity.builder()
                        .id(model.getAccount().getId())
                        .build()
                )
                .state(model.getState())
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

}