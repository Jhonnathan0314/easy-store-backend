package com.easy.store.backend.context.category.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
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
public class FindAllCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final CategoryHasPaymentTypeRepository categoryHasPaymentTypeRepository;

    public List<Category> findAll() throws NoResultsException {

        log.info("ACCION FINDALL CATEGORY -> Iniciando búsqueda");

        List<Category> categories = categoryRepository.findAll();
        if(categories == null || categories.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDALL CATEGORY -> Encontré categorias con éxito");

        for (Category category : categories) {
            category.setPaymentTypes(categoryHasPaymentTypeRepository.findByCategoryId(category.getId()));
        }

        return categories;
    }

}
