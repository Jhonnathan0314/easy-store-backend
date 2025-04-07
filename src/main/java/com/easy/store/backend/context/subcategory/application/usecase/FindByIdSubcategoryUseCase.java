package com.easy.store.backend.context.subcategory.application.usecase;

import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindByIdSubcategoryUseCase {

    private final SubcategoryRepository subcategoryRepository;

    public Subcategory findById(Long id) throws NoResultsException {

        log.info("ACCION FINDBYID SUBCATEGORY -> Iniciando búsqueda con id: {}", id);

        Optional<Subcategory> optionalSubcategory = subcategoryRepository.findById(id);
        if(optionalSubcategory.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYID SUBCATEGORY -> Encontré subcategoria con éxito");

        return optionalSubcategory.get();
    }

}
