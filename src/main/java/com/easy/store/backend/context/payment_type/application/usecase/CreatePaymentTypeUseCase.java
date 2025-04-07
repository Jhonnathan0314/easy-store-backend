package com.easy.store.backend.context.payment_type.application.usecase;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CreatePaymentTypeUseCase {

    private final PaymentTypeRepository paymentTypeRepository;

    public PaymentType create(PaymentType paymentType) throws InvalidBodyException, NoIdReceivedException, NonExistenceException {

        log.info("ACCION CREATE PAYMENT_TYPE -> Iniciando proceso con body: {}", paymentType.toString());

        if(!paymentType.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        log.info("ACCION CREATE PAYMENT_TYPE -> Validé cuerpo de la petición");

        log.info("ACCION CREATE PAYMENT_TYPE -> Creando tipo de pago");

        return paymentTypeRepository.create(paymentType);
    }

}
