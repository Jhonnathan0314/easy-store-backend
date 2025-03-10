package com.easy.store.backend.context.purchase_has_product.infrastructure.mappers;

import com.easy.store.backend.context.product.infrastructure.mappers.ProductMapper;
import com.easy.store.backend.context.purchase.infrastructure.mappers.PurchaseMapper;
import com.easy.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductDTO;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import com.easy.store.backend.context.purchase_has_product.infrastructure.persistence.PurchaseHasProductEntity;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseHasProductMapper implements Mapper<PurchaseHasProductEntity, PurchaseHasProduct, PurchaseHasProductDTO> {

    private final PurchaseMapper purchaseMapper = new PurchaseMapper();
    private final ProductMapper productMapper = new ProductMapper();

    @Override
    public PurchaseHasProduct entityToModel(PurchaseHasProductEntity entity) {
        return PurchaseHasProduct.builder()
                .id(PurchaseHasProductId.builder()
                        .purchaseId(entity.getId().getPurchaseId())
                        .productId(entity.getId().getProductId())
                        .build()
                )
                .product(productMapper.entityToModel(entity.getProduct()))
                .purchase(purchaseMapper.entityToModel(entity.getPurchase()))
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
                .product(productMapper.modelToEntity(model.getProduct()))
                .purchase(purchaseMapper.modelToEntity(model.getPurchase()))
                .quantity(model.getQuantity())
                .unitPrice(model.getUnitPrice())
                .subtotal(model.getSubtotal())
                .build();
    }

    @Override
    public PurchaseHasProductDTO modelToDto(PurchaseHasProduct model) {
        return PurchaseHasProductDTO.builder()
                .id(PurchaseHasProductId.builder()
                        .purchaseId(model.getId().getPurchaseId())
                        .productId(model.getId().getProductId())
                        .build()
                )
                .product(productMapper.modelToDto(model.getProduct()))
                .purchase(purchaseMapper.modelToDto(model.getPurchase()))
                .quantity(model.getQuantity())
                .unitPrice(model.getUnitPrice())
                .subtotal(model.getSubtotal())
                .build();
    }

    @Override
    public PurchaseHasProduct dtoToModel(PurchaseHasProductDTO dto) {
        return PurchaseHasProduct.builder()
                .id(PurchaseHasProductId.builder()
                        .purchaseId(dto.getId().getPurchaseId())
                        .productId(dto.getId().getProductId())
                        .build()
                )
                .product(productMapper.dtoToModel(dto.getProduct()))
                .purchase(purchaseMapper.dtoToModel(dto.getPurchase()))
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
    public List<PurchaseHasProductDTO> modelsToDtos(List<PurchaseHasProduct> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseHasProduct> dtosToModels(List<PurchaseHasProductDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}