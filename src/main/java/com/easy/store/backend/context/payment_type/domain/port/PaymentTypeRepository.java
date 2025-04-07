package com.easy.store.backend.context.payment_type.domain.port;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;

import java.util.List;
import java.util.Optional;

public interface PaymentTypeRepository {
    List<PaymentType> findAll();
    List<PaymentType> findAllActive();
    Optional<PaymentType> findById(Long id);
    PaymentType create(PaymentType category);
    PaymentType update(PaymentType category);
    void deleteById(Long id);
}
