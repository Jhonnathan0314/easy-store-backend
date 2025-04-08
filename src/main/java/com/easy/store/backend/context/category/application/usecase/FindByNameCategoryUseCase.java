package com.easy.store.backend.context.category.application.usecase;

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
public class FindByNameCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final CategoryHasPaymentTypeRepository categoryHasPaymentTypeRepository;

    public Optional<Category> findByName(String name) throws NoResultsException {

        log.info("ACCION FINDBYNAME CATEGORY -> Iniciando búsqueda");

        Optional<Category> optionalCategory = categoryRepository.findByName(name);;
        if(optionalCategory.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYNAME CATEGORY -> Encontré categoria con éxito");

        Category category = optionalCategory.get();

        category.setPaymentTypes(categoryHasPaymentTypeRepository.findByCategoryId(category.getId()));

        return optionalCategory;
    }

}
