package com.sophie.store.backend.context.payment_type.application.usecase;

import com.sophie.store.backend.context.payment_type.domain.model.PaymentType;
import com.sophie.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.DuplicatedException;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePaymentTypeUseCase {

    private final PaymentTypeRepository paymentTypeRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public PaymentType create(PaymentType paymentType) throws DuplicatedException, InvalidBodyException {

        if(!paymentType.isValid(paymentType)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        if(paymentTypeRepository.findByName(paymentType.getName()).isPresent()) throw new DuplicatedException(errorMessages.DUPLICATED);

        return paymentTypeRepository.create(paymentType);
    }

}
