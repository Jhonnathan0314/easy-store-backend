package com.easy.store.backend.context.payment_type.application.usecase;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByAccountIdPaymentTypeUseCase {

    private final Logger logger = Logger.getLogger(FindByAccountIdPaymentTypeUseCase.class.getName());

    private final PaymentTypeRepository paymentTypeRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<PaymentType> findByAccountId(Long accountId) throws NoResultsException {

        logger.info("ACCION FINDBYACCOUNTID PAYMENT_TYPE -> Iniciando búsqueda");

        List<PaymentType> paymentTypes = paymentTypeRepository.findByAccountId(accountId);
        if(paymentTypes == null || paymentTypes.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYACCOUNTID PAYMENT_TYPE -> Encontré tipos de pago con éxito");

        return paymentTypes;
    }

}
