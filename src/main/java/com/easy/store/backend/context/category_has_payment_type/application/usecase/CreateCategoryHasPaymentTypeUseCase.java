package com.easy.store.backend.context.category_has_payment_type.application.usecase;


import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentType;
import com.easy.store.backend.context.category_has_payment_type.domain.port.CategoryHasPaymentTypeRepository;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateCategoryHasPaymentTypeUseCase {

    private final CategoryHasPaymentTypeRepository categoryHasPaymentTypeRepository;
    private final CategoryRepository categoryRepository;
    private final PaymentTypeRepository paymentTypeRepository;

    public CategoryHasPaymentType create(CategoryHasPaymentType categoryHasPaymentType) throws NoIdReceivedException, InvalidBodyException, NoResultsException {

        log.info("ACCION CREATE CATEGORY HAS PAYMENT TYPE -> Iniciando creación con body: {}", categoryHasPaymentType.toString());

        if(categoryHasPaymentType.getId() == null) throw new NoIdReceivedException(ErrorMessages.NO_ID_RECEIVED);
        log.info("ACCION CREATE CATEGORY HAS PAYMENT TYPE -> Validé id recibido");

        if(!categoryHasPaymentType.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        log.info("ACCION CREATE CATEGORY HAS PAYMENT TYPE -> Validé cuerpo valido");

        Optional<Category> optCategory = categoryRepository.findById(categoryHasPaymentType.getId().getCategoryId());
        if(optCategory.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION CREATE CATEGORY HAS PAYMENT TYPE -> Validé categoria existente");

        Optional<PaymentType> optPaymentType = paymentTypeRepository.findById(categoryHasPaymentType.getId().getPaymentTypeId());
        if(optPaymentType.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION CREATE CATEGORY HAS PAYMENT TYPE -> Validé tipo de pago existente");

        log.info("ACCION CREATE CATEGORY HAS PAYMENT TYPE -> Creando objeto");

        return categoryHasPaymentTypeRepository.create(categoryHasPaymentType);
    }

}
