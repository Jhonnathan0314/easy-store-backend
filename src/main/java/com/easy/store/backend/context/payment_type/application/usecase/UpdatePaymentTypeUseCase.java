package com.easy.store.backend.context.payment_type.application.usecase;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoChangesException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UpdatePaymentTypeUseCase {

    private final Logger logger = Logger.getLogger(UpdatePaymentTypeUseCase.class.getName());

    private final ErrorMessages errorMessages = new ErrorMessages();
    private final PaymentTypeRepository paymentTypeRepository;

    public PaymentType update(PaymentType paymentType) throws NoIdReceivedException, NoResultsException, NoChangesException, InvalidBodyException {

        logger.info("ACCION UDPATE PAYMENT_TYPE -> Inicia el proceso");

        if(paymentType.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);
        logger.info("ACCION UDPATE PAYMENT_TYPE -> Validé id");

        if(!paymentType.isValid(paymentType)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        logger.info("ACCION UDPATE PAYMENT_TYPE -> Validé cuerpo de la petición");

        Optional<PaymentType> optPaymentType = paymentTypeRepository.findById(paymentType.getId());
        if(optPaymentType.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION UDPATE PAYMENT_TYPE -> Validé existencia del tipo de pago");

        PaymentType paymentTypeDb = optPaymentType.get();
        if(!areDifferences(paymentTypeDb, paymentType)) throw new NoChangesException(errorMessages.NO_CHANGES);
        logger.info("ACCION UDPATE PAYMENT_TYPE -> Validé que hayan cambios a aplicar");

        paymentType.setState(paymentTypeDb.getState());

        logger.info("ACCION UDPATE PAYMENT_TYPE -> Actualizando tipo de pago");

        return paymentTypeRepository.update(paymentType);
    }

    private boolean areDifferences(PaymentType paymentTypeDb, PaymentType paymentType) {
        return !paymentTypeDb.getName().equals(paymentType.getName()) ||
                !paymentTypeDb.getAccount().getId().equals(paymentType.getAccount().getId());
    }

}
