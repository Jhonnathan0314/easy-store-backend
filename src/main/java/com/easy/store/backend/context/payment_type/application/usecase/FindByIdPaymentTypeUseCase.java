package com.easy.store.backend.context.payment_type.application.usecase;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
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
