package com.sophie.store.backend.context.category.infrastructure.mappers;

import com.sophie.store.backend.context.category.application.dto.CategoryResponseDTO;
import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.sophie.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryResponseMapper implements Mapper<CategoryEntity, Category, CategoryResponseDTO> {

    @Override
    public Category entityToModel(CategoryEntity entity) {
        return Category.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    @Override
    public CategoryEntity modelToEntity(Category model) {
        return CategoryEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .build();
    }

    @Override
    public CategoryResponseDTO modelToDto(Category model) {
        return CategoryResponseDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .build();
    }

    @Override
    public Category dtoToModel(CategoryResponseDTO dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
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
    public List<CategoryResponseDTO> modelsToDtos(List<Category> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> dtosToModels(List<CategoryResponseDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}