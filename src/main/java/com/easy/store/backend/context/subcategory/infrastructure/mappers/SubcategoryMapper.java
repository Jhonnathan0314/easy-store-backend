package com.easy.store.backend.context.subcategory.infrastructure.mappers;

import com.easy.store.backend.context.category.infrastructure.mappers.CategoryMapper;
import com.easy.store.backend.context.subcategory.application.dto.SubcategoryDTO;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.infrastructure.persistence.SubcategoryEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class SubcategoryMapper extends BaseMapper<SubcategoryEntity, Subcategory, SubcategoryDTO> {

    private final CategoryMapper categoryMapper = new CategoryMapper();

    @Override
    public Subcategory entityToModel(SubcategoryEntity entity) {
        return Subcategory.builder()
                .id(entity.getId())
                .name(entity.getName())
                .category(categoryMapper.entityToModel(entity.getCategory()))
                .createBy(entity.getCreateBy())
                .updateBy(entity.getUpdateBy())
                .creationDate(entity.getCreationDate())
                .updateDate(entity.getUpdateDate())
                .state(entity.getState())
                .build();
    }

    @Override
    public SubcategoryEntity modelToEntity(Subcategory model) {
        return SubcategoryEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .category(categoryMapper.modelToEntity(model.getCategory()))
                .createBy(model.getCreateBy())
                .updateBy(model.getUpdateBy())
                .creationDate(model.getCreationDate())
                .updateDate(model.getUpdateDate())
                .state(model.getState())
                .build();
    }

    @Override
    public SubcategoryDTO modelToDto(Subcategory model) {
        return SubcategoryDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .category(categoryMapper.modelToDto(model.getCategory()))
                .createBy(model.getCreateBy())
                .updateBy(model.getUpdateBy())
                .build();
    }

    @Override
    public Subcategory dtoToModel(SubcategoryDTO dto) {
        return Subcategory.builder()
                .id(dto.getId())
                .name(dto.getName())
                .category(categoryMapper.dtoToModel(dto.getCategory()))
                .createBy(dto.getCreateBy())
                .updateBy(dto.getUpdateBy())
                .build();
    }

}