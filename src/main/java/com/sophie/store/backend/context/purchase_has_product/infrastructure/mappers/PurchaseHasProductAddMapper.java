package com.sophie.store.backend.context.purchase_has_product.infrastructure.mappers;

import com.sophie.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductAddDTO;
import com.sophie.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.sophie.store.backend.context.purchase_has_product.infrastructure.persistence.PurchaseHasProductEntity;
import com.sophie.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseHasProductAddMapper implements Mapper<PurchaseHasProductEntity, PurchaseHasProduct, PurchaseHasProductAddDTO> {

    @Override
    public PurchaseHasProduct entityToModel(PurchaseHasProductEntity entity) {
        return PurchaseHasProduct.builder()
                .quantity(entity.getQuantity())
                .build();
    }

    @Override
    public PurchaseHasProductEntity modelToEntity(PurchaseHasProduct model) {
        return PurchaseHasProductEntity.builder()
                .quantity(model.getQuantity())
                .build();
    }

    @Override
    public PurchaseHasProductAddDTO modelToDto(PurchaseHasProduct model) {
        return PurchaseHasProductAddDTO.builder()
                .quantity(model.getQuantity())
                .build();
    }

    @Override
    public PurchaseHasProduct dtoToModel(PurchaseHasProductAddDTO dto) {
        return PurchaseHasProduct.builder()
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
    public List<PurchaseHasProductAddDTO> modelsToDtos(List<PurchaseHasProduct> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseHasProduct> dtosToModels(List<PurchaseHasProductAddDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}
