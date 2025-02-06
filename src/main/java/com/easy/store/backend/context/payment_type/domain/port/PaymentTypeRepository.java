package com.easy.store.backend.context.payment_type.domain.port;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;

import java.util.List;
import java.util.Optional;

public interface PaymentTypeRepository {
    List<PaymentType> findAll();
    Optional<PaymentType> findById(Long id);
    Optional<PaymentType> findByNameAndAccountId(String name, Long accountId);
    List<PaymentType> findByAccountId(Long accountId);
    PaymentType create(PaymentType category);
    PaymentType update(PaymentType category);
    void deleteById(Long id);
}
