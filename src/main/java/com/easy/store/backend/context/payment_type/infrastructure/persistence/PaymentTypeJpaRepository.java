package com.easy.store.backend.context.payment_type.infrastructure.persistence;

import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentTypeJpaRepository extends JpaRepository<PaymentTypeEntity, Long> {

    Optional<PaymentTypeEntity> findByNameAndAccountId(String name, Long accountId);
    List<PaymentTypeEntity> findByAccountId(Long accountId);

}
