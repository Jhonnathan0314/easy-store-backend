package com.easy.store.backend.context.purchase_has_product.infrastructure.mappers;

import com.easy.store.backend.context.product.infrastructure.mappers.ProductUpdateMapper;
import com.easy.store.backend.context.purchase.infrastructure.mappers.PurchaseUpdateMapper;
import com.easy.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductUpdateDTO;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import com.easy.store.backend.context.purchase_has_product.infrastructure.persistence.PurchaseHasProductEntity;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseHasProductUpdateMapper implements Mapper<PurchaseHasProductEntity, PurchaseHasProduct, PurchaseHasProductUpdateDTO> {

    private final ProductUpdateMapper productMapper = new ProductUpdateMapper();
    private final PurchaseUpdateMapper purchaseMapper = new PurchaseUpdateMapper();

    @Override
    public PurchaseHasProduct entityToModel(PurchaseHasProductEntity entity) {
        return PurchaseHasProduct.builder()
                .id(PurchaseHasProductId.builder()
                        .purchaseId(entity.getId().getPurchaseId())
                        .productId(entity.getId().getProductId())
                        .build()
                )
                .quantity(entity.getQuantity())
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
                .product(productMapper.modelToEntity(model.getProduct()))
                .purchase(purchaseMapper.modelToEntity(model.getPurchase()))
                .quantity(model.getQuantity())
                .unitPrice(model.getUnitPrice())
                .subtotal(model.getSubtotal())
                .build();
    }

    @Override
    public PurchaseHasProductUpdateDTO modelToDto(PurchaseHasProduct model) {
        return PurchaseHasProductUpdateDTO.builder()
                .id(PurchaseHasProductId.builder()
                        .purchaseId(model.getId().getPurchaseId())
                        .productId(model.getId().getProductId())
                        .build()
                )
                .quantity(model.getQuantity())
                .build();
    }

    @Override
    public PurchaseHasProduct dtoToModel(PurchaseHasProductUpdateDTO dto) {
        return PurchaseHasProduct.builder()
                .id(PurchaseHasProductId.builder()
                        .purchaseId(dto.getId().getPurchaseId())
                        .productId(dto.getId().getProductId())
                        .build()
                )
                .quantity(dto.getQuantity())
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
    public List<PurchaseHasProductUpdateDTO> modelsToDtos(List<PurchaseHasProduct> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseHasProduct> dtosToModels(List<PurchaseHasProductUpdateDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}
