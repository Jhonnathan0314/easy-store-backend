package com.easy.store.backend.context.category.infrastructure.mappers;

import com.easy.store.backend.context.account.infrastructure.mappers.AccountMapper;
import com.easy.store.backend.context.category.application.dto.CategoryResponseDTO;
import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.easy.store.backend.context.user.infrastructure.mappers.UserResponseMapper;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryResponseMapper implements Mapper<CategoryEntity, Category, CategoryResponseDTO> {

    private final UserResponseMapper userMapper = new UserResponseMapper();
    private final AccountMapper accountMapper = new AccountMapper();

    @Override
    public Category entityToModel(CategoryEntity entity) {
        return Category.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .imageName(entity.getImageName())
                .user(userMapper.entityToModel(entity.getUser()))
                .account(accountMapper.entityToModel(entity.getAccount()))
                .build();
    }

    @Override
    public CategoryEntity modelToEntity(Category model) {
        return CategoryEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .imageName(model.getImageName())
                .user(userMapper.modelToEntity(model.getUser()))
                .account(accountMapper.modelToEntity(model.getAccount()))
                .build();
    }

    @Override
    public CategoryResponseDTO modelToDto(Category model) {
        return CategoryResponseDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .imageName(model.getImageName())
                .user(userMapper.modelToDto(model.getUser()))
                .account(accountMapper.modelToDto(model.getAccount()))
                .build();
    }

    @Override
    public Category dtoToModel(CategoryResponseDTO dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .imageName(dto.getImageName())
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