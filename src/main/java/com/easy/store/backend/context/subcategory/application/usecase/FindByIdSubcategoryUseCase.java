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
public class FindByIdSubcategoryUseCase {

    private final Logger logger = Logger.getLogger(FindByIdSubcategoryUseCase.class.getName());

    private final SubcategoryRepository subcategoryRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Subcategory findById(Long id) throws NoResultsException {

        logger.info("ACCION FINDBYID SUBCATEGORY -> Iniciando búsqueda con id: " + id);

        Optional<Subcategory> optionalSubcategory = subcategoryRepository.findById(id);
        if(optionalSubcategory.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYID SUBCATEGORY -> Encontré subcategoria con éxito");

        return optionalSubcategory.get();
    }

}
