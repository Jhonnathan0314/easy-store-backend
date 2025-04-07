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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateSubcategoryUseCase {

    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    public Subcategory update(Subcategory subcategory) throws
            NoIdReceivedException, NoResultsException, NoChangesException, InvalidBodyException {

        log.info("ACCION UDPATE SUBCATEGORY -> Inicia el proceso con body: {}", subcategory.toString());

        Optional<Category> optCategory = categoryRepository.findById(subcategory.getCategory().getId());
        if(optCategory.isEmpty()) throw new NoResultsException(ErrorMessages.NO_CATEGORY_RESULTS);
        log.info("ACCION UDPATE SUBCATEGORY -> Validé categoria existente");

        subcategory.setCategory(optCategory.get());

        if(subcategory.getId() == null) throw new NoIdReceivedException(ErrorMessages.NO_ID_RECEIVED);
        log.info("ACCION UDPATE SUBCATEGORY -> Validé id");

        if(!subcategory.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        log.info("ACCION UDPATE SUBCATEGORY -> Validé cuerpo de la petición");

        Optional<Subcategory> optSubcategory = subcategoryRepository.findById(subcategory.getId());
        if(optSubcategory.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION UDPATE SUBCATEGORY -> Validé existencia de la subcategoria");

        Subcategory subcategoryDb = optSubcategory.get();
        if(!areDifferences(subcategoryDb, subcategory)) {
            throw new NoChangesException(ErrorMessages.NO_CHANGES);
        }
        log.info("ACCION UDPATE SUBCATEGORY -> Validé que hayan cambios a aplicar");

        subcategory.setState(subcategoryDb.getState());

        log.info("ACCION UDPATE SUBCATEGORY -> Actualizando subcategoria");

        return subcategoryRepository.update(subcategory);
    }

    private boolean areDifferences(Subcategory subcategoryDb, Subcategory subcategory) {
        return !Objects.equals(subcategoryDb.getCategory().getId(), subcategory.getCategory().getId()) ||
                !subcategoryDb.getName().equals(subcategory.getName());
    }

}
