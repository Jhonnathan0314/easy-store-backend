package com.easy.store.backend.context.category.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.DuplicatedException;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CreateCategoryUseCase {

    private final Logger logger = Logger.getLogger(CreateCategoryUseCase.class.getName());

    private final CategoryRepository categoryRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Category create(Category category) throws DuplicatedException, NoIdReceivedException, InvalidBodyException {

        logger.info("ACCION CREATE CATEGORY -> Iniciando proceso con body: " + category.toString());

        if(!category.isValid(category)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        logger.info("ACCION CREATE CATEGORY -> Validé cuerpo de la petición");

        if(category.getUser().getId() == null || category.getAccount().getId() == null)
            throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);
        logger.info("ACCION CREATE CATEGORY -> Validé ids de usuario y cuenta");

        if(categoryRepository.findByName(category.getName()).isPresent()) throw new DuplicatedException(errorMessages.DUPLICATED);
        logger.info("ACCION CREATE CATEGORY -> Validé categoria no duplicada");

        logger.info("ACCION CREATE CATEGORY -> Creando categoria");

        return categoryRepository.create(category);
    }

}
