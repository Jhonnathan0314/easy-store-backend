package com.sophie.store.backend.context.payment_type.application.usecase;

import com.sophie.store.backend.context.payment_type.domain.model.PaymentType;
import com.sophie.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NonExisteceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeleteByIdPaymentTypeUseCase {

    private final PaymentTypeRepository paymentTypeRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public void deleteById(Long id) throws NonExisteceException {
        Optional<PaymentType> paymentType = paymentTypeRepository.findById(id);
        if(paymentType.isEmpty()) throw new NonExisteceException(errorMessages.NON_EXISTENT_DATA);
        paymentTypeRepository.deleteById(id);
    }

}
