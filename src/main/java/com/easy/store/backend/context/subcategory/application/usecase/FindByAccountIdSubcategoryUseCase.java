package com.easy.store.backend.context.subcategory.application.usecase;

import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindByAccountIdSubcategoryUseCase {

    private final SubcategoryRepository subcategoryRepository;

    public List<Subcategory> findByAccountId(Long accountId) throws NoResultsException {

        log.info("ACCION FINDBYACCOUNTID SUBCATEGORY -> Iniciando búsqueda");

        List<Subcategory> subcategories = subcategoryRepository.findByAccountId(accountId);
        if(subcategories == null || subcategories.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYACCOUNTID SUBCATEGORY -> Encontré subcategorias con éxito");

        return subcategories;
    }

}
