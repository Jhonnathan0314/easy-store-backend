package com.easy.store.backend.context.payment_type.application.usecase;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindByIdPaymentTypeUseCase {

    private final PaymentTypeRepository categoryRepository;

    public PaymentType findById(Long id) throws NoResultsException {

        log.info("ACCION FINDBYID PAYMENT_TYPE -> Iniciando búsqueda");

        Optional<PaymentType> optionalPaymentType = categoryRepository.findById(id);
        if(optionalPaymentType.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYID PAYMENT_TYPE -> Encontré tipo de pago con éxito");

        return optionalPaymentType.get();
    }

}
