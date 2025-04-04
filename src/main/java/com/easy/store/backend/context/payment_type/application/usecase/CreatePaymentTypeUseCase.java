package com.easy.store.backend.context.payment_type.application.usecase;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.domain.port.AccountRepository;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.DuplicatedException;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CreatePaymentTypeUseCase {

    private final Logger logger = Logger.getLogger(CreatePaymentTypeUseCase.class.getName());

    private final PaymentTypeRepository paymentTypeRepository;
    private final AccountRepository accountRepository;

    public PaymentType create(PaymentType paymentType) throws InvalidBodyException, NoIdReceivedException, NonExistenceException {

        logger.info("ACCION CREATE PAYMENT_TYPE -> Iniciando proceso con body: " + paymentType.toString());

        if(!paymentType.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        logger.info("ACCION CREATE PAYMENT_TYPE -> Validé cuerpo de la petición");

        if(paymentType.getAccount().getId() == null) throw new NoIdReceivedException(ErrorMessages.NO_ID_RECEIVED);
        logger.info("ACCION CREATE PAYMENT_TYPE -> Validé id de cuenta");

        Account account = accountRepository.findById(paymentType.getAccount().getId()).orElse(null);
        if(account == null) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION CREATE PAYMENT_TYPE -> Cuenta encontra con éxito");

        paymentType.setAccount(account);

        logger.info("ACCION CREATE PAYMENT_TYPE -> Creando tipo de pago");

        return paymentTypeRepository.create(paymentType);
    }

}
