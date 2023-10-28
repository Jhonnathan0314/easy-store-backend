package com.sophie.store.backend.context.category.infrastructure.mappers;

import com.sophie.store.backend.context.category.application.dto.CategoryCreateDTO;
import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.sophie.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryCreateMapper implements Mapper<CategoryEntity, Category, CategoryCreateDTO> {

    @Override
    public Category entityToModel(CategoryEntity entity) {
        return Category.builder()
                .name(entity.getName())
                .createBy(entity.getCreateBy())
                .build();
    }

    @Override
    public CategoryEntity modelToEntity(Category model) {
        return CategoryEntity.builder()
                .name(model.getName())
                .createBy(model.getCreateBy())
                .build();
    }

    @Override
    public CategoryCreateDTO modelToDto(Category model) {
        return CategoryCreateDTO.builder()
                .name(model.getName())
                .createBy(model.getCreateBy())
                .build();
    }

    @Override
    public Category dtoToModel(CategoryCreateDTO dto) {
        return Category.builder()
                .name(dto.getName())
                .createBy(dto.getCreateBy())
                .build();
    }

    @Override
    public List<Category> entitiesToModels(List<CategoryEntity> entities) {
        return entities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryEntity> modelsToEntities(List<Category> models) {
        return models.stream()
                .map(this::modelToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryCreateDTO> modelsToDtos(List<Category> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> dtosToModels(List<CategoryCreateDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}