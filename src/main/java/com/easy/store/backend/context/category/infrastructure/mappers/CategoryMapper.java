package com.easy.store.backend.context.category.infrastructure.mappers;

import com.easy.store.backend.context.account.infrastructure.mappers.AccountMapper;
import com.easy.store.backend.context.category.application.dto.CategoryDTO;
import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.easy.store.backend.context.user.infrastructure.mappers.UserMapper;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper implements Mapper<CategoryEntity, Category, CategoryDTO> {

    private final UserMapper userMapper = new UserMapper();
    private final AccountMapper accountMapper = new AccountMapper();

    @Override
    public Category entityToModel(CategoryEntity entity) {
        return Category.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .imageName(entity.getImageName())
                .createBy(entity.getCreateBy())
                .updateBy(entity.getUpdateBy())
                .user(userMapper.entityToModel(entity.getUser()))
                .account(accountMapper.entityToModel(entity.getAccount()))
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
                .imageName(model.getImageName())
                .createBy(model.getCreateBy())
                .updateBy(model.getUpdateBy())
                .user(userMapper.modelToEntity(model.getUser()))
                .account(accountMapper.modelToEntity(model.getAccount()))
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
                .imageName(model.getImageName())
                .createBy(model.getCreateBy())
                .updateBy(model.getUpdateBy())
                .user(userMapper.modelToDto(model.getUser()))
                .account(accountMapper.modelToDto(model.getAccount()))
                .build();
    }

    @Override
    public Category dtoToModel(CategoryDTO dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .imageName(dto.getImageName())
                .createBy(dto.getCreateBy())
                .updateBy(dto.getUpdateBy())
                .user(userMapper.dtoToModel(dto.getUser()))
                .account(accountMapper.dtoToModel(dto.getAccount()))
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