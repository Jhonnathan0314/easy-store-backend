package com.sophie.store.backend.context.subcategory.infrastructure.mappers;

import com.sophie.store.backend.context.subcategory.application.dto.SubcategoryUpdateDTO;
import com.sophie.store.backend.context.subcategory.domain.model.Subcategory;
import com.sophie.store.backend.context.subcategory.infrastructure.persistence.SubcategoryEntity;
import com.sophie.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class SubcategoryUpdateMapper implements Mapper<SubcategoryEntity, Subcategory, SubcategoryUpdateDTO> {

    @Override
    public Subcategory entityToModel(SubcategoryEntity entity) {
        return Subcategory.builder()
                .id(entity.getId())
                .name(entity.getName())
                .updateBy(entity.getUpdateBy())
                .build();
    }

    @Override
    public SubcategoryEntity modelToEntity(Subcategory model) {
        return SubcategoryEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .updateBy(model.getUpdateBy())
                .build();
    }

    @Override
    public SubcategoryUpdateDTO modelToDto(Subcategory model) {
        return SubcategoryUpdateDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .updateBy(model.getUpdateBy())
                .build();
    }

    @Override
    public Subcategory dtoToModel(SubcategoryUpdateDTO dto) {
        return Subcategory.builder()
                .id(dto.getId())
                .name(dto.getName())
                .updateBy(dto.getUpdateBy())
                .build();
    }

    @Override
    public List<Subcategory> entitiesToModels(List<SubcategoryEntity> entities) {
        return entities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubcategoryEntity> modelsToEntities(List<Subcategory> models) {
        return models.stream()
                .map(this::modelToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubcategoryUpdateDTO> modelsToDtos(List<Subcategory> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Subcategory> dtosToModels(List<SubcategoryUpdateDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}