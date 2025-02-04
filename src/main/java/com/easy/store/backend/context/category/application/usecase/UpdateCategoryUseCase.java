package com.easy.store.backend.context.category.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoChangesException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UpdateCategoryUseCase {

    private final Logger logger = Logger.getLogger(UpdateCategoryUseCase.class.getName());

    private final ErrorMessages errorMessages = new ErrorMessages();
    private final CategoryRepository categoryRepository;

    public Category update(Category category) throws NoIdReceivedException, NoResultsException, NoChangesException, InvalidBodyException {

        logger.info("ACCION UDPATE CATEGORY -> Inicia el proceso");
        if(category.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);

        logger.info("ACCION UDPATE CATEGORY -> Valide id");
        if(!category.isValid(category)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        logger.info("ACCION UDPATE CATEGORY -> Valide cuerpo de la peticion");
        if(category.getUser().getId() == null || category.getAccount().getId() == null)
            throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);

        logger.info("ACCION UDPATE CATEGORY -> Valide ids de usuario y cuenta");
        Optional<Category> optCategory = categoryRepository.findById(category.getId());
        if(optCategory.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);

        logger.info("ACCION UDPATE CATEGORY -> Valide existencia de la categoria");
        Category categoryDb = optCategory.get();
        if(!areDifferences(categoryDb, category)) throw new NoChangesException(errorMessages.NO_CHANGES);

        logger.info("ACCION UDPATE CATEGORY -> Valide existencia de la categoria");
        category.setState(categoryDb.getState());

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
