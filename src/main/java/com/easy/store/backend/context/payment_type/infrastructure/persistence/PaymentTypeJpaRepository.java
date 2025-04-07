package com.easy.store.backend.context.payment_type.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentTypeJpaRepository extends JpaRepository<PaymentTypeEntity, Long> {

    @Query(nativeQuery = true, value = "select * from payment_type where state = 'active'")
    List<PaymentTypeEntity> findAllActive();

}
