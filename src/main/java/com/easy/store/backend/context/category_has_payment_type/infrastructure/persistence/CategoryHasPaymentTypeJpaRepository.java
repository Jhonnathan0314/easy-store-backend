package com.easy.store.backend.context.category_has_payment_type.infrastructure.persistence;

import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentTypeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryHasPaymentTypeJpaRepository extends JpaRepository<CategoryHasPaymentTypeEntity, CategoryHasPaymentTypeId> {

    List<CategoryHasPaymentTypeEntity> findByCategoryId(Long categoryId);

}
