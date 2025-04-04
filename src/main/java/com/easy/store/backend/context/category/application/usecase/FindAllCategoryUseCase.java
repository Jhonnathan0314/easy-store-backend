package com.easy.store.backend.context.category.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindAllCategoryUseCase {

    private final Logger logger = Logger.getLogger(FindAllCategoryUseCase.class.getName());

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() throws NoResultsException {

        logger.info("ACCION FINDALL CATEGORY -> Iniciando búsqueda");

        List<Category> categories = categoryRepository.findAll();
        if(categories == null || categories.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        logger.info("ACCION FINDALL CATEGORY -> Encontré categorias con éxito");

        return categories;
    }

}
