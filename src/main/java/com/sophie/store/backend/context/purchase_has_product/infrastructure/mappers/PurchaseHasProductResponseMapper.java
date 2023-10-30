package com.sophie.store.backend.context.purchase_has_product.infrastructure.mappers;

import com.sophie.store.backend.context.product.infrastructure.mappers.ProductResponseMapper;
import com.sophie.store.backend.context.purchase.infrastructure.mappers.PurchaseResponseMapper;
import com.sophie.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductResponseDTO;
import com.sophie.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.sophie.store.backend.context.purchase_has_product.infrastructure.persistence.PurchaseHasProductEntity;
import com.sophie.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseHasProductResponseMapper implements Mapper<PurchaseHasProductEntity, PurchaseHasProduct, PurchaseHasProductResponseDTO> {

    private final PurchaseResponseMapper purchaseMapper = new PurchaseResponseMapper();
    private final ProductResponseMapper productMapper = new ProductResponseMapper();

    @Override
    public PurchaseHasProduct entityToModel(PurchaseHasProductEntity entity) {
        return PurchaseHasProduct.builder()
                .id(entity.getId())
                .product(productMapper.entityToModel(entity.getProduct()))
                .purchase(purchaseMapper.entityToModel(entity.getPurchase()))
                .quantity(entity.getQuantity())
                .build();
    }

    @Override
    public PurchaseHasProductEntity modelToEntity(PurchaseHasProduct model) {
        return PurchaseHasProductEntity.builder()
                .id(model.getId())
                .product(productMapper.modelToEntity(model.getProduct()))
                .purchase(purchaseMapper.modelToEntity(model.getPurchase()))
                .quantity(model.getQuantity())
                .build();
    }

    @Override
    public PurchaseHasProductResponseDTO modelToDto(PurchaseHasProduct model) {
        return PurchaseHasProductResponseDTO.builder()
                .id(model.getId())
                .product(productMapper.modelToDto(model.getProduct()))
                .purchase(purchaseMapper.modelToDto(model.getPurchase()))
                .quantity(model.getQuantity())
                .build();
    }

    @Override
    public PurchaseHasProduct dtoToModel(PurchaseHasProductResponseDTO dto) {
        return PurchaseHasProduct.builder()
                .id(dto.getId())
                .product(productMapper.dtoToModel(dto.getProduct()))
                .purchase(purchaseMapper.dtoToModel(dto.getPurchase()))
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