package com.easy.store.backend.context.purchase.infrastructure.mappers;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.context.purchase.application.dto.PurchaseGenerateDTO;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseGenerateMapper extends BaseMapper<PurchaseEntity, Purchase, PurchaseGenerateDTO> {

    @Override
    public Purchase entityToModel(PurchaseEntity entity) {
        return Purchase.builder()
                .user(User.builder()
                        .id(entity.getUser().getId())
                        .build()
                )
                .paymentType(PaymentType.builder()
                        .id(entity.getPaymentType().getId())
                        .build()
                )
                .category(Category.builder()
                        .id(entity.getCategory().getId())
                        .build()
                )
                .createBy(entity.getCreateBy())
                .state(entity.getState())
                .build();
    }

    @Override
    public PurchaseEntity modelToEntity(Purchase model) {
        return PurchaseEntity.builder()
                .user(UserEntity.builder()
                        .id(model.getUser().getId())
                        .build()
                )
                .paymentType(PaymentTypeEntity.builder()
                        .id(model.getPaymentType().getId())
                        .build()
                )
                .category(CategoryEntity.builder()
                        .id(model.getCategory().getId())
                        .build()
                )
                .createBy(model.getCreateBy())
                .state(model.getState())
                .total(model.getTotal())
                .build();
    }

    @Override
    public PurchaseGenerateDTO modelToDto(Purchase model) {
        return PurchaseGenerateDTO.builder()
                .userId(model.getUser().getId())
                .paymentTypeId(model.getPaymentType().getId())
                .categoryId(model.getCategory().getId())
                .createBy(model.getCreateBy())
                .state(model.getState())
                .build();
    }

    @Override
    public Purchase dtoToModel(PurchaseGenerateDTO dto) {
        return Purchase.builder()
                .user(User.builder()
                        .id(dto.getUserId())
                        .build()
                )
                .paymentType(PaymentType.builder()
                        .id(dto.getPaymentTypeId())
                        .build()
                )
                .category(Category.builder()
                        .id(dto.getCategoryId())
                        .build()
                )
                .createBy(dto.getCreateBy())
                .state(dto.getState())
                .build();
    }

}