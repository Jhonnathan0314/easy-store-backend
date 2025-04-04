package com.easy.store.backend.context.payment_type.infrastructure.mappers;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.infrastructure.persistence.AccountEntity;
import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeResponseDTO;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentTypeResponseMapper extends BaseMapper<PaymentTypeEntity, PaymentType, PaymentTypeResponseDTO> {

    @Override
    public PaymentType entityToModel(PaymentTypeEntity entity) {
        return PaymentType.builder()
                .id(entity.getId())
                .name(entity.getName())
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
                .account(AccountEntity.builder()
                        .id(model.getAccount().getId())
                        .build()
                )
                .state(model.getState())
                .build();
    }

    @Override
    public PaymentTypeResponseDTO modelToDto(PaymentType model) {
        return PaymentTypeResponseDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .accountId(model.getAccount().getId())
                .build();
    }

    @Override
    public PaymentType dtoToModel(PaymentTypeResponseDTO dto) {
        return PaymentType.builder()
                .id(dto.getId())
                .name(dto.getName())
                .account(Account.builder()
                        .id(dto.getAccountId())
                        .build()
                )
                .build();
    }

}