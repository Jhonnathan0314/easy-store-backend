package com.easy.store.backend.context.category_has_payment_type.application.usecase;


import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentType;
import com.easy.store.backend.context.category_has_payment_type.domain.port.CategoryHasPaymentTypeRepository;
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

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateCategoryHasPaymentTypeUseCase {

    private final CategoryHasPaymentTypeRepository categoryHasPaymentTypeRepository;
    private final CategoryRepository categoryRepository;
    private final PaymentTypeRepository paymentTypeRepository;

    public CategoryHasPaymentType update(CategoryHasPaymentType categoryHasPaymentType) throws NoIdReceivedException, InvalidBodyException, NoResultsException, NoChangesException {

        log.info("ACCION UPDATE CATEGORY HAS PAYMENT TYPE -> Iniciando creación con body: {}", categoryHasPaymentType.toString());

        if(categoryHasPaymentType.getId() == null) throw new NoIdReceivedException(ErrorMessages.NO_ID_RECEIVED);
        log.info("ACCION UPDATE CATEGORY HAS PAYMENT TYPE -> Validé id recibido");

        if(!categoryHasPaymentType.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        log.info("ACCION UPDATE CATEGORY HAS PAYMENT TYPE -> Validé cuerpo valido");

        Optional<Category> optCategory = categoryRepository.findById(categoryHasPaymentType.getId().getCategoryId());
        if(optCategory.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION UPDATE CATEGORY HAS PAYMENT TYPE -> Validé categoria existente");

        Optional<PaymentType> optPaymentType = paymentTypeRepository.findById(categoryHasPaymentType.getId().getPaymentTypeId());
        if(optPaymentType.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION UPDATE CATEGORY HAS PAYMENT TYPE -> Validé tipo de pago existente");

        Optional<CategoryHasPaymentType> optCategoryHasPaymentType = categoryHasPaymentTypeRepository.findById(categoryHasPaymentType.getId());
        if(optCategoryHasPaymentType.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION UPDATE CATEGORY HAS PAYMENT TYPE -> Validé objeto existente");

        CategoryHasPaymentType categoryHasPaymentTypeDb = optCategoryHasPaymentType.get();
        if(!areDifferences(categoryHasPaymentTypeDb, categoryHasPaymentType)) throw new NoChangesException(ErrorMessages.NO_CHANGES);
        log.info("ACCION UPDATE CATEGORY HAS PAYMENT TYPE -> Validé que hay diferencias");

        categoryHasPaymentType.setState(categoryHasPaymentTypeDb.getState());

        log.info("ACCION UPDATE CATEGORY HAS PAYMENT TYPE -> Actualizando objeto");

        return categoryHasPaymentTypeRepository.update(categoryHasPaymentType);
    }

    // Se usa Objects.equals() en vez de .equals() directo: phone, email, accountNumber,
    // accountType y accountBank son todos campos opcionales en CategoryHasPaymentTypeUpdateDto
    // (sin @NotNull/@NotBlank) y en CategoryHasPaymentType.isValid() (que solo valida el id). Un
    // cliente que actualizara solo alguno de estos campos, dejando los demas en null, provocaba
    // un NullPointerException que terminaba en un 500 generico.
    private boolean areDifferences(CategoryHasPaymentType categoryHasPaymentTypeDb, CategoryHasPaymentType categoryHasPaymentType) {
        return !Objects.equals(categoryHasPaymentTypeDb.getPhone(), categoryHasPaymentType.getPhone()) ||
                !Objects.equals(categoryHasPaymentTypeDb.getEmail(), categoryHasPaymentType.getEmail()) ||
                !Objects.equals(categoryHasPaymentTypeDb.getAccountNumber(), categoryHasPaymentType.getAccountNumber()) ||
                !Objects.equals(categoryHasPaymentTypeDb.getAccountType(), categoryHasPaymentType.getAccountType()) ||
                !Objects.equals(categoryHasPaymentTypeDb.getAccountBank(), categoryHasPaymentType.getAccountBank());
    }

}
