package com.easy.store.backend.context.category.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.DuplicatedException;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CreateCategoryUseCase {

    private final CategoryRepository categoryRepository;

    public Category create(Category category) throws DuplicatedException, NoIdReceivedException, InvalidBodyException {

        log.info("ACCION CREATE CATEGORY -> Iniciando proceso con body: {}", category.toString());

        if(!category.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        log.info("ACCION CREATE CATEGORY -> Validé cuerpo de la petición");

        if(category.getUser().getId() == null || category.getAccount().getId() == null)
            throw new NoIdReceivedException(ErrorMessages.NO_ID_RECEIVED);
        log.info("ACCION CREATE CATEGORY -> Validé ids de usuario y cuenta");

        if(categoryRepository.findByName(category.getName()).isPresent()) throw new DuplicatedException(ErrorMessages.DUPLICATED);
        log.info("ACCION CREATE CATEGORY -> Validé categoria no duplicada");

        log.info("ACCION CREATE CATEGORY -> Creando categoria");

        return categoryRepository.create(category);
    }

}
