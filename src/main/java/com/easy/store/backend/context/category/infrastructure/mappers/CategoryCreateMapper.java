package com.easy.store.backend.context.category.infrastructure.mappers;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.infrastructure.persistence.AccountEntity;
import com.easy.store.backend.context.category.application.dto.CategoryCreateDTO;
import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class CategoryCreateMapper extends BaseMapper<CategoryEntity, Category, CategoryCreateDTO> {

    @Override
    public Category entityToModel(CategoryEntity entity) {
        return Category.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .imageName(entity.getImageName())
                .createBy(entity.getCreateBy())
                .user(User.builder()
                        .id(entity.getUser().getId())
                        .build()
                )
                .account(Account.builder()
                        .id(entity.getAccount().getId())
                        .build()
                )
                .build();
    }

    @Override
    public CategoryEntity modelToEntity(Category model) {
        return CategoryEntity.builder()
                .name(model.getName())
                .description(model.getDescription())
                .imageName(model.getImageName())
                .createBy(model.getCreateBy())
                .user(UserEntity.builder()
                        .id(model.getUser().getId())
                        .build()
                )
                .account(AccountEntity.builder()
                        .id(model.getAccount().getId())
                        .build()
                )
                .build();
    }

    @Override
    public CategoryCreateDTO modelToDto(Category model) {
        return CategoryCreateDTO.builder()
                .name(model.getName())
                .description(model.getDescription())
                .imageName(model.getImageName())
                .createBy(model.getCreateBy())
                .userId(model.getUser().getId())
                .accountId(model.getAccount().getId())
                .build();
    }

    @Override
    public Category dtoToModel(CategoryCreateDTO dto) {
        return Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .imageName(dto.getImageName())
                .createBy(dto.getCreateBy())
                .user(User.builder()
                        .id(dto.getUserId())
                        .build()
                )
                .account(Account.builder()
                        .id(dto.getAccountId())
                        .build()
                )
                .build();
    }

}