package com.sophie.store.backend.context.purchase.infrastructure.mappers;

import com.sophie.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeMapper;
import com.sophie.store.backend.context.purchase.application.dto.PurchaseDTO;
import com.sophie.store.backend.context.purchase.domain.model.Purchase;
import com.sophie.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import com.sophie.store.backend.context.user.infrastructure.mappers.UserMapper;
import com.sophie.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseMapper implements Mapper<PurchaseEntity, Purchase, PurchaseDTO> {

    private final UserMapper userMapper = new UserMapper();
    private final PaymentTypeMapper paymentTypeMapper = new PaymentTypeMapper();

    @Override
    public Purchase entityToModel(PurchaseEntity entity) {
        return Purchase.builder()
                .id(entity.getId())
                .user(userMapper.entityToModel(entity.getUser()))
                .paymentType(paymentTypeMapper.entityToModel(entity.getPaymentType()))
                .total(entity.getTotal())
                .date(entity.getDate())
                .createBy(entity.getCreateBy())
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
                .createBy(model.getCreateBy())
                .build();
    }

    @Override
    public PurchaseDTO modelToDto(Purchase model) {
        return PurchaseDTO.builder()
                .id(model.getId())
                .user(userMapper.modelToDto(model.getUser()))
                .paymentType(paymentTypeMapper.modelToDto(model.getPaymentType()))
                .total(model.getTotal())
                .date(model.getDate())
                .createBy(model.getCreateBy())
                .build();
    }

    @Override
    public Purchase dtoToModel(PurchaseDTO dto) {
        return Purchase.builder()
                .id(dto.getId())
                .user(userMapper.dtoToModel(dto.getUser()))
                .paymentType(paymentTypeMapper.dtoToModel(dto.getPaymentType()))
                .total(dto.getTotal())
                .date(dto.getDate())
                .createBy(dto.getCreateBy())
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
    public List<PurchaseDTO> modelsToDtos(List<Purchase> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Purchase> dtosToModels(List<PurchaseDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}