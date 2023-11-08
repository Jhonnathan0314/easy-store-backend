package com.sophie.store.backend.context.payment_type.application.usecase;

import com.sophie.store.backend.context.payment_type.domain.model.PaymentType;
import com.sophie.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangeStateByIdPaymentTypeUseCase {

    private final PaymentTypeRepository paymentTypeRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public PaymentType changeStateById(Long id) throws NonExistenceException {
        Optional<PaymentType> optPaymentType = paymentTypeRepository.findById(id);
        if(optPaymentType.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        PaymentType paymentType = optPaymentType.get();
        paymentType.setState(paymentType.getState().equals("active") ? "inactive" : "active");
        paymentType = paymentTypeRepository.update(paymentType);
        return paymentType;
    }

}
