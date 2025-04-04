package com.easy.store.backend.context.payment_type.application.usecase;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DeleteByIdPaymentTypeUseCase {

    private final Logger logger = Logger.getLogger(DeleteByIdPaymentTypeUseCase.class.getName());

    private final PaymentTypeRepository paymentTypeRepository;

    public void deleteById(Long id) throws NonExistenceException {

        logger.info("ACCION DELETEBYID PAYMENT_TYPE -> Iniciando proceso con id: " + id);

        Optional<PaymentType> paymentType = paymentTypeRepository.findById(id);
        if(paymentType.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION DELETEBYID PAYMENT_TYPE -> Tipo de pago encontrado con éxito");

        logger.info("ACCION DELETEBYID PAYMENT_TYPE -> Eliminando tipo de pago");

        paymentTypeRepository.deleteById(id);
    }

}
