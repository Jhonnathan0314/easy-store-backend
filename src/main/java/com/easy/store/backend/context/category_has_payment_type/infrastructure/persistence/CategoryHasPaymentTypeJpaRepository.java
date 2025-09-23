package com.easy.store.backend.context.category_has_payment_type.infrastructure.persistence;

import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryHasPaymentTypeJpaRepository extends JpaRepository<CategoryHasPaymentTypeEntity, CategoryHasPaymentTypeId> {

    List<CategoryHasPaymentTypeEntity> findByCategoryId(Long categoryId);

    @Query(nativeQuery = true, value = "SELECT chpt.* FROM category_has_payment_type chpt " +
            "JOIN payment_type pt on pt.id = chpt.payment_type_id " +
            "JOIN category c on c.id = chpt.category_id " +
            "JOIN account a on a.id = c.account_id " +
            "WHERE a.id = ?1")
    List<CategoryHasPaymentTypeEntity> findByAccountId(Long accountId);

}
