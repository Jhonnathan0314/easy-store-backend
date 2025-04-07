package com.easy.store.backend.context.payment_type.application.usecase;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindAllPaymentTypeUseCase {

    private final PaymentTypeRepository paymentTypeRepository;

    public List<PaymentType> findAll() throws NoResultsException {

        log.info("ACCION FINDALL PAYMENT_TYPE -> Iniciando búsqueda");

        List<PaymentType> paymentTypes = paymentTypeRepository.findAll();
        if(paymentTypes == null || paymentTypes.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDALL PAYMENT_TYPE -> Encontré tipos de pago con éxito");

        return paymentTypes;
    }

}
