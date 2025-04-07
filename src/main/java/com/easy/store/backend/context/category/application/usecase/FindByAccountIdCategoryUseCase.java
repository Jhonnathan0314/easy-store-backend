package com.easy.store.backend.context.category.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindByAccountIdCategoryUseCase {

    private final CategoryRepository categoryRepository;

    public List<Category> findByAccountId(Long accountId) throws NoResultsException {

        log.info("ACCION FINDBYACCOUNTID CATEGORY -> Iniciando búsqueda");

        List<Category> categories = categoryRepository.findByAccountId(accountId);
        if(categories == null || categories.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYACCOUNTID CATEGORY -> Encontré categorias con éxito");

        return categories;
    }

}
