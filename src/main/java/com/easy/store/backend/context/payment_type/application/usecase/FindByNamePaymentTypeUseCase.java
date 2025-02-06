package com.easy.store.backend.context.payment_type.application.usecase;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindByNamePaymentTypeUseCase {

    private final PaymentTypeRepository paymentTypeRepository;

    public Optional<PaymentType> findByNameAndAccountId(String name, Long accountId) {
        return paymentTypeRepository.findByNameAndAccountId(name, accountId);
    }

}
