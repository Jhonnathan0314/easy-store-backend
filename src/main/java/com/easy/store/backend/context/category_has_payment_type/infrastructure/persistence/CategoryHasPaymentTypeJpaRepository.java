package com.easy.store.backend.context.category_has_payment_type.infrastructure.persistence;

import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryHasPaymentTypeJpaRepository extends JpaRepository<CategoryHasPaymentTypeEntity, CategoryHasPaymentTypeId> {

    @Query(nativeQuery = true, value = "select * from category_has_payment_type where state = 'active' and category_id = ?1")
    List<CategoryHasPaymentTypeEntity> findActiveByCategoryId(Long categoryId);

}
