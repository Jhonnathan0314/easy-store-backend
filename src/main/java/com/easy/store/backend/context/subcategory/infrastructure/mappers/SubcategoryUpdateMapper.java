package com.easy.store.backend.context.subcategory.infrastructure.mappers;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.easy.store.backend.context.subcategory.application.dto.SubcategoryUpdateDTO;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.infrastructure.persistence.SubcategoryEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class SubcategoryUpdateMapper extends BaseMapper<SubcategoryEntity, Subcategory, SubcategoryUpdateDTO> {

    @Override
    public Subcategory entityToModel(SubcategoryEntity entity) {
        return Subcategory.builder()
                .id(entity.getId())
                .name(entity.getName())
                .category(Category.builder()
                        .id(entity.getCategory().getId())
                        .build()
                )
                .updateBy(entity.getUpdateBy())
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
                .updateBy(model.getUpdateBy())
                .category(CategoryEntity.builder()
                        .id(model.getCategory().getId())
                        .build()
                )
                .build();
    }

    @Override
    public SubcategoryUpdateDTO modelToDto(Subcategory model) {
        return SubcategoryUpdateDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .categoryId(model.getCategory().getId())
                .updateBy(model.getUpdateBy())
                .categoryId(model.getCategory().getId())
                .build();
    }

    @Override
    public Subcategory dtoToModel(SubcategoryUpdateDTO dto) {
        return Subcategory.builder()
                .id(dto.getId())
                .name(dto.getName())
                .category(Category.builder()
                        .id(dto.getCategoryId())
                        .build()
                )
                .updateBy(dto.getUpdateBy())
                .category(Category.builder()
                        .id(dto.getCategoryId())
                        .build()
                )
                .build();
    }

}