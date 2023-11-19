package com.sophie.store.backend.context.category.infrastructure.mappers;

import com.sophie.store.backend.context.category.application.dto.CategoryDTO;
import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.sophie.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper implements Mapper<CategoryEntity, Category, CategoryDTO> {

    @Override
    public Category entityToModel(CategoryEntity entity) {
        return Category.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createBy(entity.getCreateBy())
                .updateBy(entity.getUpdateBy())
                .creationDate(entity.getCreationDate())
                .updateDate(entity.getUpdateDate())
                .state(entity.getState())
                .build();
    }

    @Override
    public CategoryEntity modelToEntity(Category model) {
        return CategoryEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .createBy(model.getCreateBy())
                .updateBy(model.getUpdateBy())
                .creationDate(model.getCreationDate())
                .updateDate(model.getUpdateDate())
                .state(model.getState())
                .build();
    }

    @Override
    public CategoryDTO modelToDto(Category model) {
        return CategoryDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .createBy(model.getCreateBy())
                .updateBy(model.getUpdateBy())
                .build();
    }

    @Override
    public Category dtoToModel(CategoryDTO dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .createBy(dto.getCreateBy())
                .updateBy(dto.getUpdateBy())
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
    public List<CategoryDTO> modelsToDtos(List<Category> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> dtosToModels(List<CategoryDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}