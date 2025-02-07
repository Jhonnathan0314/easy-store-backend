package com.easy.store.backend.context.payment_type.application.usecase;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByNameAndAccountIdPaymentTypeUseCase {

    private final Logger logger = Logger.getLogger(FindByNameAndAccountIdPaymentTypeUseCase.class.getName());

    private final PaymentTypeRepository paymentTypeRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Optional<PaymentType> findByNameAndAccountId(String name, Long accountId) throws NoResultsException {

        logger.info("ACCION FINDBYNAMEANDACCOUNTID PAYMENT_TYPE -> Iniciando búsqueda");

        Optional<PaymentType> optionalpaymentType = paymentTypeRepository.findByNameAndAccountId(name, accountId);;
        if(optionalpaymentType.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYNAMEANDACCOUNTID PAYMENT_TYPE -> Encontré tipo de pago con éxito");

        return optionalpaymentType;
    }

}
