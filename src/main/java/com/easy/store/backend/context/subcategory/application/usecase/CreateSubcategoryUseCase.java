package com.easy.store.backend.context.subcategory.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.DuplicatedException;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CreateSubcategoryUseCase {

    private final Logger logger = Logger.getLogger(CreateSubcategoryUseCase.class.getName());

    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Subcategory create(Subcategory subcategory) throws
            NoResultsException, DuplicatedException, InvalidBodyException {

        logger.info("ACCION CREATE SUBCATEGORY -> Iniciando proceso con body: " + subcategory.toString());

        Optional<Category> optCategory = categoryRepository.findById(subcategory.getCategory().getId());
        if(optCategory.isEmpty()) throw new NoResultsException(errorMessages.NO_CATEGORY_RESULTS);
        logger.info("ACCION CREATE SUBCATEGORY -> Validé categoria existente");

        subcategory.setCategory(optCategory.get());

        if(!subcategory.isValid(subcategory)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        logger.info("ACCION CREATE SUBCATEGORY -> Validé cuerpo de la petición");

        if(subcategoryRepository.findByName(subcategory.getName()).isPresent())
            throw new DuplicatedException(errorMessages.DUPLICATED);
        logger.info("ACCION CREATE SUBCATEGORY -> Validé subcategoria no duplicada");

        logger.info("ACCION CREATE SUBCATEGORY -> Creando subcategoria");

        return subcategoryRepository.create(subcategory);
    }

}
