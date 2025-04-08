package com.easy.store.backend.context.category_has_payment_type.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentType;
import com.easy.store.backend.context.category_has_payment_type.domain.port.CategoryHasPaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindByCategoryIdCategoryHasPaymentTypeUseCase {

    private final CategoryHasPaymentTypeRepository categoryHasPaymentTypeRepository;
    private final CategoryRepository categoryRepository;

    public List<CategoryHasPaymentType> findByCategoryId(Long categoryId) throws NoResultsException {

        log.info("ACCION FINDBYCATEGORYID CATEGORY HAS PAYMENT TYPE -> Iniciando busqueda");

        Optional<Category> optCategory = categoryRepository.findById(categoryId);
        if(optCategory.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYCATEGORYID CATEGORY HAS PAYMENT TYPE -> Validé categoria existente");

        List<CategoryHasPaymentType> categoryPaymentTypes = categoryHasPaymentTypeRepository.findByCategoryId(categoryId);
        if(categoryPaymentTypes.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);

        log.info("ACCION FINDBYCATEGORYID CATEGORY HAS PAYMENT TYPE -> Encontré con éxito");

        return categoryPaymentTypes;
    }

}
