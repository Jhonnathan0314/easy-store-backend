package com.easy.store.backend.context.category.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DeleteByIdCategoryUseCase {

    private final Logger logger = Logger.getLogger(DeleteByIdCategoryUseCase.class.getName());

    private final CategoryRepository categoryRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public void deleteById(Long id) throws NonExistenceException {

        logger.info("ACCION DELETEBYID CATEGORY -> Iniciando proceso con id: " + id);

        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION DELETEBYID CATEGORY -> Categoria encontrada con éxito");

        logger.info("ACCION DELETEBYID CATEGORY -> Eliminando categoria");

        categoryRepository.deleteById(id);
    }

}
