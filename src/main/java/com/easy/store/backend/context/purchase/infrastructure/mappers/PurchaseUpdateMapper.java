package com.easy.store.backend.context.purchase.infrastructure.mappers;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.context.purchase.application.dto.PurchaseUpdateDTO;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseUpdateMapper implements Mapper<PurchaseEntity, Purchase, PurchaseUpdateDTO> {

    @Override
    public Purchase entityToModel(PurchaseEntity entity) {
        return Purchase.builder()
                .id(entity.getId())
                .user(User.builder()
                        .id(entity.getUser().getId())
                        .build()
                )
                .paymentType(PaymentType.builder()
                        .id(entity.getPaymentType().getId())
                        .build()
                )
                .createBy(entity.getCreateBy())
                .state(entity.getState())
                .build();
    }

    @Override
    public PurchaseEntity modelToEntity(Purchase model) {
        return PurchaseEntity.builder()
                .id(model.getId())
                .user(UserEntity.builder()
                        .id(model.getUser().getId())
                        .build()
                )
                .paymentType(PaymentTypeEntity.builder()
                        .id(model.getPaymentType().getId())
                        .build()
                )
                .createBy(model.getCreateBy())
                .state(model.getState())
                .build();
    }

    @Override
    public PurchaseUpdateDTO modelToDto(Purchase model) {
        return PurchaseUpdateDTO.builder()
                .id(model.getId())
                .userId(model.getUser().getId())
                .paymentTypeId(model.getPaymentType().getId())
                .updateBy(model.getCreateBy())
                .state(model.getState())
                .build();
    }

    @Override
    public Purchase dtoToModel(PurchaseUpdateDTO dto) {
        return Purchase.builder()
                .id(dto.getId())
                .user(User.builder()
                        .id(dto.getUserId())
                        .build()
                )
                .paymentType(PaymentType.builder()
                        .id(dto.getPaymentTypeId())
                        .build()
                )
                .updateBy(dto.getUpdateBy())
                .state(dto.getState())
                .build();
    }

    @Override
    public List<Purchase> entitiesToModels(List<PurchaseEntity> entities) {
        return entities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseEntity> modelsToEntities(List<Purchase> models) {
        return models.stream()
                .map(this::modelToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseUpdateDTO> modelsToDtos(List<Purchase> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Purchase> dtosToModels(List<PurchaseUpdateDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}