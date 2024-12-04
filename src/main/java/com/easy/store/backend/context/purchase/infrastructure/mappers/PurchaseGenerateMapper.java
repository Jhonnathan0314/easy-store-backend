package com.easy.store.backend.context.purchase.infrastructure.mappers;

import com.easy.store.backend.context.purchase.application.dto.PurchaseGenerateDTO;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseGenerateMapper implements Mapper<PurchaseEntity, Purchase, PurchaseGenerateDTO> {

    @Override
    public Purchase entityToModel(PurchaseEntity entity) {
        return Purchase.builder()
                .createBy(entity.getCreateBy())
                .build();
    }

    @Override
    public PurchaseEntity modelToEntity(Purchase model) {
        return PurchaseEntity.builder()
                .createBy(model.getCreateBy())
                .build();
    }

    @Override
    public PurchaseGenerateDTO modelToDto(Purchase model) {
        return PurchaseGenerateDTO.builder()
                .createBy(model.getCreateBy())
                .build();
    }

    @Override
    public Purchase dtoToModel(PurchaseGenerateDTO dto) {
        return Purchase.builder()
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
    public List<PurchaseGenerateDTO> modelsToDtos(List<Purchase> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Purchase> dtosToModels(List<PurchaseGenerateDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}