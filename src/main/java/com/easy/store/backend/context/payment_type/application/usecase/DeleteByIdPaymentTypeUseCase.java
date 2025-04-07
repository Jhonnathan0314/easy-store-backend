package com.easy.store.backend.context.payment_type.application.usecase;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteByIdPaymentTypeUseCase {

    private final PaymentTypeRepository paymentTypeRepository;

    public void deleteById(Long id) throws NonExistenceException {

        log.info("ACCION DELETEBYID PAYMENT_TYPE -> Iniciando proceso con id: {}", id);

        Optional<PaymentType> paymentType = paymentTypeRepository.findById(id);
        if(paymentType.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        log.info("ACCION DELETEBYID PAYMENT_TYPE -> Tipo de pago encontrado con éxito");

        log.info("ACCION DELETEBYID PAYMENT_TYPE -> Eliminando tipo de pago");

        paymentTypeRepository.deleteById(id);
    }

}
