package com.easy.store.backend.context.subcategory.infrastructure.mappers;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.easy.store.backend.context.subcategory.application.dto.SubcategoryCreateDTO;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.infrastructure.persistence.SubcategoryEntity;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class SubcategoryCreateMapper implements Mapper<SubcategoryEntity, Subcategory, SubcategoryCreateDTO> {

    @Override
    public Subcategory entityToModel(SubcategoryEntity entity) {
        return Subcategory.builder()
                .name(entity.getName())
                .createBy(entity.getCreateBy())
                .category(Category.builder()
                        .id(entity.getCategory().getId())
                        .build()
                )
                .build();
    }

    @Override
    public SubcategoryEntity modelToEntity(Subcategory model) {
        return SubcategoryEntity.builder()
                .name(model.getName())
                .createBy(model.getCreateBy())
                .category(CategoryEntity.builder()
                        .id(model.getCategory().getId())
                        .build()
                )
                .build();
    }

    @Override
    public SubcategoryCreateDTO modelToDto(Subcategory model) {
        return SubcategoryCreateDTO.builder()
                .name(model.getName())
                .createBy(model.getCreateBy())
                .categoryId(model.getCategory().getId())
                .build();
    }

    @Override
    public Subcategory dtoToModel(SubcategoryCreateDTO dto) {
        return Subcategory.builder()
                .name(dto.getName())
                .createBy(dto.getCreateBy())
                .category(Category.builder()
                        .id(dto.getCategoryId())
                        .build()
                )
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
    public List<SubcategoryCreateDTO> modelsToDtos(List<Subcategory> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Subcategory> dtosToModels(List<SubcategoryCreateDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}