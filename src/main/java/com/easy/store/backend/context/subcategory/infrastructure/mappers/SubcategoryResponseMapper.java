package com.easy.store.backend.context.subcategory.infrastructure.mappers;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.infrastructure.mappers.CategoryResponseMapper;
import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.easy.store.backend.context.subcategory.application.dto.SubcategoryResponseDTO;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.infrastructure.persistence.SubcategoryEntity;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class SubcategoryResponseMapper implements Mapper<SubcategoryEntity, Subcategory, SubcategoryResponseDTO> {

    private final CategoryResponseMapper responseMapper = new CategoryResponseMapper();

    @Override
    public Subcategory entityToModel(SubcategoryEntity entity) {
        return Subcategory.builder()
                .id(entity.getId())
                .name(entity.getName())
                .category(Category.builder()
                        .id(entity.getCategory().getId())
                        .build()
                )
                .build();
    }

    @Override
    public SubcategoryEntity modelToEntity(Subcategory model) {
        return SubcategoryEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .category(CategoryEntity.builder()
                        .id(model.getCategory().getId())
                        .build()
                )
                .build();
    }

    @Override
    public SubcategoryResponseDTO modelToDto(Subcategory model) {
        return SubcategoryResponseDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .categoryId(model.getCategory().getId())
                .build();
    }

    @Override
    public Subcategory dtoToModel(SubcategoryResponseDTO dto) {
        return Subcategory.builder()
                .id(dto.getId())
                .name(dto.getName())
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
    public List<SubcategoryResponseDTO> modelsToDtos(List<Subcategory> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Subcategory> dtosToModels(List<SubcategoryResponseDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}