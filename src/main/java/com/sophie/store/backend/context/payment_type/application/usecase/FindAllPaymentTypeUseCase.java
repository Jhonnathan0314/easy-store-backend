package com.sophie.store.backend.context.payment_type.application.usecase;

import com.sophie.store.backend.context.payment_type.domain.model.PaymentType;
import com.sophie.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllPaymentTypeUseCase {

    private final PaymentTypeRepository paymentTypeRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<PaymentType> findAll() throws NoResultsException {
        List<PaymentType> paymentTypes = paymentTypeRepository.findAll();
        if(paymentTypes == null || paymentTypes.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        return paymentTypes;
    }

}
