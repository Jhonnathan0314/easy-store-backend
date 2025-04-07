package com.easy.store.backend.context.subcategory.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class CreateSubcategoryUseCase {

    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    public Subcategory create(Subcategory subcategory) throws NoResultsException, InvalidBodyException {

        log.info("ACCION CREATE SUBCATEGORY -> Iniciando proceso con body: {}", subcategory.toString());

        Optional<Category> optCategory = categoryRepository.findById(subcategory.getCategory().getId());
        if(optCategory.isEmpty()) throw new NoResultsException(ErrorMessages.NO_CATEGORY_RESULTS);
        log.info("ACCION CREATE SUBCATEGORY -> Validé categoria existente");

        subcategory.setCategory(optCategory.get());

        if(!subcategory.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        log.info("ACCION CREATE SUBCATEGORY -> Validé cuerpo de la petición");

        log.info("ACCION CREATE SUBCATEGORY -> Creando subcategoria");

        return subcategoryRepository.create(subcategory);
    }

}
