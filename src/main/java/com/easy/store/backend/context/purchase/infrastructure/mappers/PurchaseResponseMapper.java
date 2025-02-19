package com.easy.store.backend.context.purchase.infrastructure.mappers;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.context.purchase.application.dto.PurchaseResponseDTO;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import com.easy.store.backend.context.purchase_has_product.infrastructure.mappers.PurchaseHasProductResponseMapper;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseResponseMapper implements Mapper<PurchaseEntity, Purchase, PurchaseResponseDTO> {

    private final PurchaseHasProductResponseMapper hasProductResponseMapper = new PurchaseHasProductResponseMapper();

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
                .category(Category.builder()
                        .id(entity.getCategory().getId())
                        .build()
                )
                .total(entity.getTotal())
                .state(entity.getState())
                .products(new ArrayList<>())
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
                .category(CategoryEntity.builder()
                        .id(model.getCategory().getId())
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
                .categoryId(model.getCategory().getId())
                .total(model.getTotal())
                .state(model.getState())
                .creationDate(model.getCreationDate())
                .products(hasProductResponseMapper.modelsToDtos(model.getProducts()))
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
                .category(Category.builder()
                        .id(dto.getCategoryId())
                        .build()
                )
                .total(dto.getTotal())
                .state(dto.getState())
                .creationDate(dto.getCreationDate())
                .products(new ArrayList<>())
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