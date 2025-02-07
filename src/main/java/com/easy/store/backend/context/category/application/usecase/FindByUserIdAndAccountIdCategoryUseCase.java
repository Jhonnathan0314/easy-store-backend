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
public class FindByUserIdAndAccountIdCategoryUseCase {

    private final Logger logger = Logger.getLogger(FindByUserIdAndAccountIdCategoryUseCase.class.getName());

    private final CategoryRepository categoryRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<Category> findByUserIdAndAccountId(Long userId, Long accountId) throws NoResultsException {

        logger.info("ACCION FINDBYUSERIDANDACCOUNTID CATEGORY -> Iniciando búsqueda");

        List<Category> categories = categoryRepository.findByUserIdAndAccountId(userId, accountId);
        if(categories == null || categories.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYUSERIDANDACCOUNTID CATEGORY -> Encontré categorias con éxito");

        return categories;
    }

}
