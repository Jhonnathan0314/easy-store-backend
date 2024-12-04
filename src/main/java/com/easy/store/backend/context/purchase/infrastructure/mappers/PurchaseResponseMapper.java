package com.easy.store.backend.context.purchase.infrastructure.mappers;

import com.easy.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeMapper;
import com.easy.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeResponseMapper;
import com.easy.store.backend.context.purchase.application.dto.PurchaseResponseDTO;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import com.easy.store.backend.context.user.infrastructure.mappers.UserResponseMapper;
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
                .user(userMapper.entityToModel(entity.getUser()))
                .paymentType(paymentTypeMapper.entityToModel(entity.getPaymentType()))
                .total(entity.getTotal())
                .date(entity.getDate())
                .build();
    }

    @Override
    public PurchaseEntity modelToEntity(Purchase model) {
        return PurchaseEntity.builder()
                .id(model.getId())
                .user(userMapper.modelToEntity(model.getUser()))
                .paymentType(paymentTypeMapper.modelToEntity(model.getPaymentType()))
                .total(model.getTotal())
                .date(model.getDate())
                .build();
    }

    @Override
    public PurchaseResponseDTO modelToDto(Purchase model) {
        return PurchaseResponseDTO.builder()
                .id(model.getId())
                .user(userMapper.modelToDto(model.getUser()))
                .paymentType(paymentTypeMapper.modelToDto(model.getPaymentType()))
                .total(model.getTotal())
                .date(model.getDate())
                .build();
    }

    @Override
    public Purchase dtoToModel(PurchaseResponseDTO dto) {
        return Purchase.builder()
                .id(dto.getId())
                .user(userMapper.dtoToModel(dto.getUser()))
                .paymentType(paymentTypeMapper.dtoToModel(dto.getPaymentType()))
                .total(dto.getTotal())
                .date(dto.getDate())
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