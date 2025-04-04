package com.easy.store.backend.context.purchase.infrastructure.mappers;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.context.purchase.application.dto.PurchaseUpdateDTO;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class PurchaseUpdateMapper extends BaseMapper<PurchaseEntity, Purchase, PurchaseUpdateDTO> {

    @Override
    public Purchase entityToModel(PurchaseEntity entity) {
        return Purchase.builder()
                .id(entity.getId())
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
                .updateBy(entity.getUpdateBy())
                .state(entity.getState())
                .build();
    }

    @Override
    public PurchaseEntity modelToEntity(Purchase model) {
        return PurchaseEntity.builder()
                .id(model.getId())
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
                .updateBy(model.getUpdateBy())
                .state(model.getState())
                .total(model.getTotal())
                .build();
    }

    @Override
    public PurchaseUpdateDTO modelToDto(Purchase model) {
        return PurchaseUpdateDTO.builder()
                .id(model.getId())
                .paymentTypeId(model.getPaymentType().getId())
                .updateBy(model.getCreateBy())
                .state(model.getState())
                .build();
    }

    @Override
    public Purchase dtoToModel(PurchaseUpdateDTO dto) {
        return Purchase.builder()
                .id(dto.getId())
                .paymentType(PaymentType.builder()
                        .id(dto.getPaymentTypeId())
                        .build()
                )
                .updateBy(dto.getUpdateBy())
                .state(dto.getState())
                .build();
    }

}