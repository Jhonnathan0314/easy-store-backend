package com.easy.store.backend.context.category.infrastructure.mappers;

import com.easy.store.backend.context.account.infrastructure.mappers.AccountCreateMapper;
import com.easy.store.backend.context.category.application.dto.CategoryCreateDTO;
import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.easy.store.backend.context.user.infrastructure.mappers.UserCreateMapper;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryCreateMapper implements Mapper<CategoryEntity, Category, CategoryCreateDTO> {

    private final UserCreateMapper userMapper = new UserCreateMapper();
    private final AccountCreateMapper accountMapper = new AccountCreateMapper();

    @Override
    public Category entityToModel(CategoryEntity entity) {
        return Category.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .imageName(entity.getImageName())
                .createBy(entity.getCreateBy())
                .user(userMapper.entityToModel(entity.getUser()))
                .account(accountMapper.entityToModel(entity.getAccount()))
                .build();
    }

    @Override
    public CategoryEntity modelToEntity(Category model) {
        return CategoryEntity.builder()
                .name(model.getName())
                .description(model.getDescription())
                .imageName(model.getImageName())
                .createBy(model.getCreateBy())
                .user(userMapper.modelToEntity(model.getUser()))
                .account(accountMapper.modelToEntity(model.getAccount()))
                .build();
    }

    @Override
    public CategoryCreateDTO modelToDto(Category model) {
        return CategoryCreateDTO.builder()
                .name(model.getName())
                .description(model.getDescription())
                .imageName(model.getImageName())
                .createBy(model.getCreateBy())
                .user(userMapper.modelToDto(model.getUser()))
                .account(accountMapper.modelToDto(model.getAccount()))
                .build();
    }

    @Override
    public Category dtoToModel(CategoryCreateDTO dto) {
        return Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .imageName(dto.getImageName())
                .createBy(dto.getCreateBy())
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