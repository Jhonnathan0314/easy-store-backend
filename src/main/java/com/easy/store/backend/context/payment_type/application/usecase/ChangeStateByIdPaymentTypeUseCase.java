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
public class ChangeStateByIdPaymentTypeUseCase {

    private final Logger logger = Logger.getLogger(ChangeStateByIdPaymentTypeUseCase.class.getName());

    private final PaymentTypeRepository paymentTypeRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public PaymentType changeStateById(Long id) throws NonExistenceException {

        logger.info("ACCION CHANGESTATEBYID PAYMENT_TYPE -> Iniciando proceso con id: " + id);

        Optional<PaymentType> optPaymentType = paymentTypeRepository.findById(id);
        if(optPaymentType.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION CHANGESTATEBYID PAYMENT_TYPE -> Tipo de pago encontrado con éxito");

        PaymentType paymentType = optPaymentType.get();
        paymentType.setState(paymentType.getState().equals("active") ? "inactive" : "active");

        logger.info("ACCION CHANGESTATEBYID PAYMENT_TYPE -> Actualizando estado");
        return paymentTypeRepository.update(paymentType);
    }

}
