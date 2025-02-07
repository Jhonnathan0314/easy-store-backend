package com.easy.store.backend.context.purchase_has_product.infrastructure.mappers;

import com.easy.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductResponseDTO;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import com.easy.store.backend.context.purchase_has_product.infrastructure.persistence.PurchaseHasProductEntity;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseHasProductResponseMapper implements Mapper<PurchaseHasProductEntity, PurchaseHasProduct, PurchaseHasProductResponseDTO> {

    @Override
    public PurchaseHasProduct entityToModel(PurchaseHasProductEntity entity) {
        return PurchaseHasProduct.builder()
                .id(PurchaseHasProductId.builder()
                        .purchaseId(entity.getId().getPurchaseId())
                        .productId(entity.getId().getProductId())
                        .build()
                )
                .quantity(entity.getQuantity())
                .unitPrice(entity.getUnitPrice())
                .subtotal(entity.getSubtotal())
                .build();
    }

    @Override
    public PurchaseHasProductEntity modelToEntity(PurchaseHasProduct model) {
        return PurchaseHasProductEntity.builder()
                .id(PurchaseHasProductId.builder()
                        .purchaseId(model.getId().getPurchaseId())
                        .productId(model.getId().getProductId())
                        .build()
                )
                .quantity(model.getQuantity())
                .unitPrice(model.getUnitPrice())
                .subtotal(model.getSubtotal())
                .build();
    }

    @Override
    public PurchaseHasProductResponseDTO modelToDto(PurchaseHasProduct model) {
        return PurchaseHasProductResponseDTO.builder()
                .id(PurchaseHasProductId.builder()
                        .purchaseId(model.getId().getPurchaseId())
                        .productId(model.getId().getProductId())
                        .build()
                )
                .quantity(model.getQuantity())
                .unitPrice(model.getUnitPrice())
                .subtotal(model.getSubtotal())
                .build();
    }

    @Override
    public PurchaseHasProduct dtoToModel(PurchaseHasProductResponseDTO dto) {
        return PurchaseHasProduct.builder()
                .id(PurchaseHasProductId.builder()
                        .purchaseId(dto.getId().getPurchaseId())
                        .productId(dto.getId().getProductId())
                        .build()
                )
                .quantity(dto.getQuantity())
                .unitPrice(dto.getUnitPrice())
                .subtotal(dto.getSubtotal())
                .build();
    }

    @Override
    public List<PurchaseHasProduct> entitiesToModels(List<PurchaseHasProductEntity> entities) {
        return entities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseHasProductEntity> modelsToEntities(List<PurchaseHasProduct> models) {
        return models.stream()
                .map(this::modelToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseHasProductResponseDTO> modelsToDtos(List<PurchaseHasProduct> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseHasProduct> dtosToModels(List<PurchaseHasProductResponseDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}