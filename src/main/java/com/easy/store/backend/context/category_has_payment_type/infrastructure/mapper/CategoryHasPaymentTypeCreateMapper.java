package com.easy.store.backend.context.category_has_payment_type.infrastructure.mapper;

import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.easy.store.backend.context.category_has_payment_type.application.dto.CategoryHasPaymentTypeCreateDto;
import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentType;
import com.easy.store.backend.context.category_has_payment_type.infrastructure.persistence.CategoryHasPaymentTypeEntity;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class CategoryHasPaymentTypeCreateMapper extends BaseMapper<CategoryHasPaymentTypeEntity, CategoryHasPaymentType, CategoryHasPaymentTypeCreateDto> {

    @Override
    public CategoryHasPaymentType entityToModel(CategoryHasPaymentTypeEntity entity) {
        return CategoryHasPaymentType.builder()
                .id(entity.getId())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .accountNumber(entity.getAccountNumber())
                .accountType(entity.getAccountType())
                .accountBank(entity.getAccountBank())
                .state(entity.getState())
                .build();
    }

    @Override
    public CategoryHasPaymentTypeEntity modelToEntity(CategoryHasPaymentType model) {
        return CategoryHasPaymentTypeEntity.builder()
                .id(model.getId())
                .category(CategoryEntity.builder()
                        .id(model.getId().getCategoryId())
                        .build()
                )
                .paymentType(PaymentTypeEntity.builder()
                        .id(model.getId().getPaymentTypeId())
                        .build()
                )
                .phone(model.getPhone())
                .email(model.getEmail())
                .accountNumber(model.getAccountNumber())
                .accountType(model.getAccountType())
                .accountBank(model.getAccountBank())
                .state(model.getState())
                .build();
    }

    @Override
    public CategoryHasPaymentTypeCreateDto modelToDto(CategoryHasPaymentType model) {
        return CategoryHasPaymentTypeCreateDto.builder()
                .id(model.getId())
                .phone(model.getPhone())
                .email(model.getEmail())
                .accountNumber(model.getAccountNumber())
                .accountType(model.getAccountType())
                .accountBank(model.getAccountBank())
                .build();
    }

    @Override
    public CategoryHasPaymentType dtoToModel(CategoryHasPaymentTypeCreateDto dto) {
        return CategoryHasPaymentType.builder()
                .id(dto.getId())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .accountNumber(dto.getAccountNumber())
                .accountType(dto.getAccountType())
                .accountBank(dto.getAccountBank())
                .build();
    }

}
