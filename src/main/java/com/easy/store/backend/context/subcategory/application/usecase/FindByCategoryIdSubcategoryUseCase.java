package com.easy.store.backend.context.subcategory.application.usecase;

import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByCategoryIdSubcategoryUseCase {

    private final Logger logger = Logger.getLogger(FindByCategoryIdSubcategoryUseCase.class.getName());

    private final SubcategoryRepository subcategoryRepository;

    public List<Subcategory> findByCategoryId(Long categoryId) throws NoResultsException {

        logger.info("ACCION FINDBYCATEGORYID SUBCATEGORY -> Iniciando búsqueda");

        List<Subcategory> subcategories = subcategoryRepository.findByCategoryId(categoryId);
        if(subcategories == null || subcategories.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYCATEGORYID SUBCATEGORY -> Encontré subcategorias con éxito");

        return subcategories;
    }

}
