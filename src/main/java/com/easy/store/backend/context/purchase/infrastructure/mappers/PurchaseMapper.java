package com.easy.store.backend.context.purchase.infrastructure.mappers;

import com.easy.store.backend.context.category.infrastructure.mappers.CategoryMapper;
import com.easy.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeMapper;
import com.easy.store.backend.context.purchase.application.dto.PurchaseDTO;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import com.easy.store.backend.context.user.infrastructure.mappers.UserMapper;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class PurchaseMapper extends BaseMapper<PurchaseEntity, Purchase, PurchaseDTO> {

    private final UserMapper userMapper = new UserMapper();
    private final PaymentTypeMapper paymentTypeMapper = new PaymentTypeMapper();
    private final CategoryMapper categoryMapper = new CategoryMapper();

    @Override
    public Purchase entityToModel(PurchaseEntity entity) {
        return Purchase.builder()
                .id(entity.getId())
                .user(userMapper.entityToModel(entity.getUser()))
                .paymentType(paymentTypeMapper.entityToModel(entity.getPaymentType()))
                .category(categoryMapper.entityToModel(entity.getCategory()))
                .total(entity.getTotal())
                .state(entity.getState())
                .creationDate(entity.getCreationDate())
                .createBy(entity.getCreateBy())
                .updateDate(entity.getUpdateDate())
                .updateBy(entity.getUpdateBy())
                .build();
    }

    @Override
    public PurchaseEntity modelToEntity(Purchase model) {
        return PurchaseEntity.builder()
                .id(model.getId())
                .user(userMapper.modelToEntity(model.getUser()))
                .paymentType(paymentTypeMapper.modelToEntity(model.getPaymentType()))
                .category(categoryMapper.modelToEntity(model.getCategory()))
                .total(model.getTotal())
                .state(model.getState())
                .creationDate(model.getCreationDate())
                .createBy(model.getCreateBy())
                .updateDate(model.getUpdateDate())
                .updateBy(model.getUpdateBy())
                .build();
    }

    @Override
    public PurchaseDTO modelToDto(Purchase model) {
        return PurchaseDTO.builder()
                .id(model.getId())
                .user(userMapper.modelToDto(model.getUser()))
                .paymentType(paymentTypeMapper.modelToDto(model.getPaymentType()))
                .category(categoryMapper.modelToDto(model.getCategory()))
                .total(model.getTotal())
                .state(model.getState())
                .creationDate(model.getCreationDate())
                .createBy(model.getCreateBy())
                .updateDate(model.getUpdateDate())
                .updateBy(model.getUpdateBy())
                .build();
    }

    @Override
    public Purchase dtoToModel(PurchaseDTO dto) {
        return Purchase.builder()
                .id(dto.getId())
                .user(userMapper.dtoToModel(dto.getUser()))
                .paymentType(paymentTypeMapper.dtoToModel(dto.getPaymentType()))
                .category(categoryMapper.dtoToModel(dto.getCategory()))
                .total(dto.getTotal())
                .state(dto.getState())
                .creationDate(dto.getCreationDate())
                .createBy(dto.getCreateBy())
                .updateDate(dto.getUpdateDate())
                .updateBy(dto.getUpdateBy())
                .build();
    }

}