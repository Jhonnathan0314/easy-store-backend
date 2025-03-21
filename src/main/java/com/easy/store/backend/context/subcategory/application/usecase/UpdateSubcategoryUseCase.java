package com.easy.store.backend.context.subcategory.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoChangesException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UpdateSubcategoryUseCase {

    private final Logger logger = Logger.getLogger(UpdateSubcategoryUseCase.class.getName());

    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Subcategory update(Subcategory subcategory) throws
            NoIdReceivedException, NoResultsException, NoChangesException, InvalidBodyException {

        logger.info("ACCION UDPATE SUBCATEGORY -> Inicia el proceso con body: " + subcategory.toString());

        Optional<Category> optCategory = categoryRepository.findById(subcategory.getCategory().getId());
        if(optCategory.isEmpty()) throw new NoResultsException(errorMessages.NO_CATEGORY_RESULTS);
        logger.info("ACCION UDPATE SUBCATEGORY -> Validé categoria existente");

        subcategory.setCategory(optCategory.get());

        if(subcategory.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);
        logger.info("ACCION UDPATE SUBCATEGORY -> Validé id");

        if(!subcategory.isValid(subcategory)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        logger.info("ACCION UDPATE SUBCATEGORY -> Validé cuerpo de la petición");

        Optional<Subcategory> optSubcategory = subcategoryRepository.findById(subcategory.getId());
        if(optSubcategory.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION UDPATE SUBCATEGORY -> Validé existencia de la subcategoria");

        Subcategory subcategoryDb = optSubcategory.get();
        if(!areDifferences(subcategoryDb, subcategory)) {
            throw new NoChangesException(errorMessages.NO_CHANGES);
        }
        logger.info("ACCION UDPATE SUBCATEGORY -> Validé que hayan cambios a aplicar");

        subcategory.setState(subcategoryDb.getState());

        logger.info("ACCION UDPATE SUBCATEGORY -> Actualizando subcategoria");

        return subcategoryRepository.update(subcategory);
    }

    private boolean areDifferences(Subcategory subcategoryDb, Subcategory subcategory) {
        return !Objects.equals(subcategoryDb.getCategory().getId(), subcategory.getCategory().getId()) ||
                !subcategoryDb.getName().equals(subcategory.getName());
    }

}
