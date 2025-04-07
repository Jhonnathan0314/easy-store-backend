package com.easy.store.backend.context.payment_type.application.usecase;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoChangesException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UpdatePaymentTypeUseCase {

    private final PaymentTypeRepository paymentTypeRepository;

    public PaymentType update(PaymentType paymentType) throws NoIdReceivedException, NoResultsException, NoChangesException, InvalidBodyException {

        log.info("ACCION UDPATE PAYMENT_TYPE -> Inicia el proceso");

        if(paymentType.getId() == null) throw new NoIdReceivedException(ErrorMessages.NO_ID_RECEIVED);
        log.info("ACCION UDPATE PAYMENT_TYPE -> Validé id");

        if(!paymentType.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        log.info("ACCION UDPATE PAYMENT_TYPE -> Validé cuerpo de la petición");

        Optional<PaymentType> optPaymentType = paymentTypeRepository.findById(paymentType.getId());
        if(optPaymentType.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION UDPATE PAYMENT_TYPE -> Validé existencia del tipo de pago");

        PaymentType paymentTypeDb = optPaymentType.get();
        if(!areDifferences(paymentTypeDb, paymentType)) throw new NoChangesException(ErrorMessages.NO_CHANGES);
        log.info("ACCION UDPATE PAYMENT_TYPE -> Validé que hayan cambios a aplicar");

        paymentType.setState(paymentTypeDb.getState());

        log.info("ACCION UDPATE PAYMENT_TYPE -> Actualizando tipo de pago");

        return paymentTypeRepository.update(paymentType);
    }

    private boolean areDifferences(PaymentType paymentTypeDb, PaymentType paymentType) {
        return !paymentTypeDb.getName().equals(paymentType.getName());
    }

}
