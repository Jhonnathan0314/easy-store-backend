package com.easy.store.backend.context.category.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoChangesException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateCategoryUseCase {

    private final CategoryRepository categoryRepository;

    public Category update(Category category) throws
            NoIdReceivedException, NoResultsException, NoChangesException, InvalidBodyException {

        log.info("ACCION UDPATE CATEGORY -> Inicia el proceso");

        if(category.getId() == null) throw new NoIdReceivedException(ErrorMessages.NO_ID_RECEIVED);
        log.info("ACCION UDPATE CATEGORY -> Validé id");

        if(!category.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        log.info("ACCION UDPATE CATEGORY -> Validé cuerpo de la petición");

        if(category.getUser().getId() == null || category.getAccount().getId() == null)
            throw new NoIdReceivedException(ErrorMessages.NO_ID_RECEIVED);
        log.info("ACCION UDPATE CATEGORY -> Validé ids de usuario y cuenta");

        Optional<Category> optCategory = categoryRepository.findById(category.getId());
        if(optCategory.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION UDPATE CATEGORY -> Validé existencia de la categoria");

        Category categoryDb = optCategory.get();
        if(!areDifferences(categoryDb, category)) throw new NoChangesException(ErrorMessages.NO_CHANGES);
        log.info("ACCION UDPATE CATEGORY -> Validé que hayan cambios a aplicar");

        category.setState(categoryDb.getState());

        log.info("ACCION UDPATE CATEGORY -> Actualizando categoria");

        return categoryRepository.update(category);
    }

    private boolean areDifferences(Category categoryDb, Category category) {
        return !categoryDb.getName().equals(category.getName()) ||
                !categoryDb.getDescription().equals(category.getDescription()) ||
                !categoryDb.getImageName().equals(category.getImageName()) ||
                !categoryDb.getUser().getId().equals(category.getUser().getId()) ||
                !categoryDb.getAccount().getId().equals(category.getAccount().getId());
    }

}
