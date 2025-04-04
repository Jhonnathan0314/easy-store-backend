package com.easy.store.backend.context.payment_type.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentTypeJpaRepository extends JpaRepository<PaymentTypeEntity, Long> {

    List<PaymentTypeEntity> findByAccountId(Long accountId);

}
