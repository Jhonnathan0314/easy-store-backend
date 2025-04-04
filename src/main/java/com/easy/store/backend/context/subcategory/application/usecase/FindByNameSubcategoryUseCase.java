package com.easy.store.backend.context.subcategory.application.usecase;

import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByNameSubcategoryUseCase {

    private final Logger logger = Logger.getLogger(FindByNameSubcategoryUseCase.class.getName());

    private final SubcategoryRepository subcategoryRepository;

    public Optional<Subcategory> findByName(String name) throws NoResultsException {

        logger.info("ACCION FINDBYNAME SUBCATEGORY -> Iniciando búsqueda");

        Optional<Subcategory> optionalSubcategory = subcategoryRepository.findByName(name);;
        if(optionalSubcategory.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYNAME SUBCATEGORY -> Encontré categoria con éxito");

        return optionalSubcategory;
    }

}
