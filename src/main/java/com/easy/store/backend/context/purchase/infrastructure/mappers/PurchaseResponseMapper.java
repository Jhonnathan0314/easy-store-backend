package com.easy.store.backend.context.purchase.infrastructure.mappers;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeMapper;
import com.easy.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeResponseMapper;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.context.purchase.application.dto.PurchaseResponseDTO;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.infrastructure.mappers.UserResponseMapper;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseResponseMapper implements Mapper<PurchaseEntity, Purchase, PurchaseResponseDTO> {

    private final UserResponseMapper userMapper = new UserResponseMapper();
    private final PaymentTypeResponseMapper paymentTypeMapper = new PaymentTypeResponseMapper();

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
                .total(entity.getTotal())
                .state(entity.getState())
                .creationDate(entity.getCreationDate())
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
                .total(model.getTotal())
                .state(model.getState())
                .creationDate(model.getCreationDate())
                .build();
    }

    @Override
    public PurchaseResponseDTO modelToDto(Purchase model) {
        return PurchaseResponseDTO.builder()
                .id(model.getId())
                .userId(model.getUser().getId())
                .paymentTypeId(model.getPaymentType().getId())
                .total(model.getTotal())
                .state(model.getState())
                .creationDate(model.getCreationDate())
                .build();
    }

    @Override
    public Purchase dtoToModel(PurchaseResponseDTO dto) {
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
                .total(dto.getTotal())
                .state(dto.getState())
                .creationDate(dto.getCreationDate())
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
    public List<PurchaseResponseDTO> modelsToDtos(List<Purchase> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Purchase> dtosToModels(List<PurchaseResponseDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}