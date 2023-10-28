package com.sophie.store.backend.context.payment_type.application.usecase;

import com.sophie.store.backend.context.payment_type.domain.model.PaymentType;
import com.sophie.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
import com.sophie.store.backend.utils.exceptions.NoChangesException;
import com.sophie.store.backend.utils.exceptions.NoIdReceivedException;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdatePaymentTypeUseCase {

    private final ErrorMessages errorMessages = new ErrorMessages();
    private final PaymentTypeRepository paymentTypeRepository;

    public PaymentType update(PaymentType paymentType) throws NoIdReceivedException, NoResultsException, NoChangesException, InvalidBodyException {

        if(paymentType.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);

        if(!paymentType.isValid(paymentType)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        Optional<PaymentType> optPaymentType = paymentTypeRepository.findById(paymentType.getId());
        if(optPaymentType.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);

        PaymentType paymentTypeDb = optPaymentType.get();
        if(paymentTypeDb.getName().equals(paymentType.getName())) throw new NoChangesException(errorMessages.NO_CHANGES);

        paymentType.setState(paymentTypeDb.getState());

        return paymentTypeRepository.update(paymentType);
    }

}
