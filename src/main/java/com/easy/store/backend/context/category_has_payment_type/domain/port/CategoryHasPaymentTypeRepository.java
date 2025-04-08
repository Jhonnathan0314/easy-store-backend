package com.easy.store.backend.context.category_has_payment_type.domain.port;

import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentType;
import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentTypeId;

import java.util.List;
import java.util.Optional;

public interface CategoryHasPaymentTypeRepository {

    List<CategoryHasPaymentType> findAll();
    List<CategoryHasPaymentType> findByCategoryId(Long categoryId);
    Optional<CategoryHasPaymentType> findById(CategoryHasPaymentTypeId id);
    CategoryHasPaymentType create(CategoryHasPaymentType categoryHasPaymentType);
    CategoryHasPaymentType update(CategoryHasPaymentType categoryHasPaymentType);

}
