package com.easy.store.backend.context.category.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteByIdCategoryUseCase {

    private final CategoryRepository categoryRepository;

    public void deleteById(Long id) throws NonExistenceException {

        log.info("ACCION DELETEBYID CATEGORY -> Iniciando proceso con id: {}", id);

        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        log.info("ACCION DELETEBYID CATEGORY -> Categoria encontrada con éxito");

        log.info("ACCION DELETEBYID CATEGORY -> Eliminando categoria");

        categoryRepository.deleteById(id);
    }

}
