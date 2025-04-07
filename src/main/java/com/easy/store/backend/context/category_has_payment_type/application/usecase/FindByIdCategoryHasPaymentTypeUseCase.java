package com.easy.store.backend.context.category_has_payment_type.application.usecase;


import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentType;
import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentTypeId;
import com.easy.store.backend.context.category_has_payment_type.domain.port.CategoryHasPaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindByIdCategoryHasPaymentTypeUseCase {

    private final CategoryHasPaymentTypeRepository categoryHasPaymentTypeRepository;

    public CategoryHasPaymentType findById(CategoryHasPaymentTypeId id) throws NoResultsException {

        log.info("ACCION FINDBYID CATEGORY HAS PAYMENT TYPE -> Iniciando busqueda con id: {}", id);

        Optional<CategoryHasPaymentType> categoryHasPaymentType = categoryHasPaymentTypeRepository.findById(id);
        if(categoryHasPaymentType.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);

        log.info("ACCION FINDBYID CATEGORY HAS PAYMENT TYPE -> Encontré con éxito");

        return categoryHasPaymentType.get();
    }

}
