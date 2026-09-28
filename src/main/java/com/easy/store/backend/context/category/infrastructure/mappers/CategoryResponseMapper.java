package com.easy.store.backend.context.category.infrastructure.mappers;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.infrastructure.persistence.AccountEntity;
import com.easy.store.backend.context.category.application.dto.CategoryResponseDTO;
import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.easy.store.backend.context.category_has_payment_type.infrastructure.mapper.CategoryHasPaymentTypeResponseMapper;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import com.easy.store.backend.utils.constants.FileConstants;
import com.easy.store.backend.utils.mappers.BaseMapper;

import java.util.ArrayList;

public class CategoryResponseMapper extends BaseMapper<CategoryEntity, Category, CategoryResponseDTO> {

    private final CategoryHasPaymentTypeResponseMapper categoryHasPaymentTypeResponseMapper = new CategoryHasPaymentTypeResponseMapper();

    @Override
    public Category entityToModel(CategoryEntity entity) {
        return Category.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .imageName(entity.getImageName())
                .user(User.builder()
                        .id(entity.getUser().getId())
                        .build()
                )
                .account(Account.builder()
                        .id(entity.getAccount().getId())
                        .build()
                )
                .paymentTypes(new ArrayList<>())
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
                .user(UserEntity.builder()
                        .id(model.getUser().getId())
                        .build()
                )
                .account(AccountEntity.builder()
                        .id(model.getAccount().getId())
                        .build()
                )
                .state(model.getState())
                .build();
    }

    @Override
    public CategoryResponseDTO modelToDto(Category model) {
        return CategoryResponseDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .imageName(model.getImageName())
                .imageUrl(buildImageUrl(model))
                .userId(model.getUser().getId())
                .accountId(model.getAccount().getId())
                .paymentTypes(new ArrayList<>())
                .paymentTypes(categoryHasPaymentTypeResponseMapper.modelsToDtos(model.getPaymentTypes()))
                .build();
    }

    /**
     * La imagen ya no viaja embebida (base64) en el listado: se sirve por su propia URL de
     * recurso (GetCategoryImageUseCase / CategoryController), lo que habilita cache HTTP real del
     * navegador y evita una llamada a S3 por categoria dentro de cada request de listado.
     */
    private String buildImageUrl(Category model) {
        String imageName = model.getImageName();
        if (imageName == null || imageName.equals(FileConstants.DEFAULT_CATEGORY_IMG)) return null;
        return "/api/v1/category/" + model.getId() + "/image/" + imageName;
    }

    @Override
    public Category dtoToModel(CategoryResponseDTO dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .imageName(dto.getImageName())
                .user(User.builder()
                        .id(dto.getUserId())
                        .build()
                )
                .account(Account.builder()
                        .id(dto.getAccountId())
                        .build()
                )
                .paymentTypes(categoryHasPaymentTypeResponseMapper.dtosToModels(dto.getPaymentTypes()))
                .build();
    }

}