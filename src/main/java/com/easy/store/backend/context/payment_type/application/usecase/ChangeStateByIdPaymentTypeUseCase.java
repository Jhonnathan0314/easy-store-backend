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
public class ChangeStateByIdPaymentTypeUseCase {

    private final PaymentTypeRepository paymentTypeRepository;

    public PaymentType changeStateById(Long id, Long updateBy) throws NonExistenceException {

        log.info("ACCION CHANGESTATEBYID PAYMENT_TYPE -> Iniciando proceso con id: {}", id);

        Optional<PaymentType> optPaymentType = paymentTypeRepository.findById(id);
        if(optPaymentType.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        log.info("ACCION CHANGESTATEBYID PAYMENT_TYPE -> Tipo de pago encontrado con éxito");

        PaymentType paymentType = optPaymentType.get();
        paymentType.setState(paymentType.getState().equals("active") ? "inactive" : "active");
        paymentType.setUpdateBy(updateBy);

        log.info("ACCION CHANGESTATEBYID PAYMENT_TYPE -> Actualizando estado");
        return paymentTypeRepository.update(paymentType);
    }

}
