package com.easy.store.backend.context.category_has_payment_type.application.usecase;

import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentType;
import com.easy.store.backend.context.category_has_payment_type.domain.port.CategoryHasPaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindAllCategoryHasPaymentTypeUseCase {

    private final CategoryHasPaymentTypeRepository categoryHasPaymentTypeRepository;

    public List<CategoryHasPaymentType> findAll() throws NoResultsException {

        log.info("ACCION FINDALL CATEGORY HAS PAYMENT TYPE -> Iniciando busqueda");

        List<CategoryHasPaymentType> categories = categoryHasPaymentTypeRepository.findAll();
        if(categories.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);

        log.info("ACCION FINDALL CATEGORY HAS PAYMENT TYPE -> Encontré con éxito");

        return categories;
    }

}
