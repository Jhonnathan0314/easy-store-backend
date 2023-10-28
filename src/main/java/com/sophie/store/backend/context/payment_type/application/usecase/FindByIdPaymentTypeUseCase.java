package com.sophie.store.backend.context.payment_type.application.usecase;

import com.sophie.store.backend.context.payment_type.domain.model.PaymentType;
import com.sophie.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindByIdPaymentTypeUseCase {

    private final PaymentTypeRepository categoryRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public PaymentType findById(Long id) throws NoResultsException {
        Optional<PaymentType> optionalPaymentType = categoryRepository.findById(id);
        if(optionalPaymentType.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        return optionalPaymentType.get();
    }

}
