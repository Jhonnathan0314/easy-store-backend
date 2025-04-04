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
public class ChangeStateByIdCategoryUseCase {

    private final Logger logger = Logger.getLogger(ChangeStateByIdCategoryUseCase.class.getName());

    private final CategoryRepository categoryRepository;

    public Category changeStateById(Long id, Long updateBy) throws NonExistenceException {

        logger.info("ACCION CHANGESTATEBYID CATEGORY -> Iniciando proceso con id: " + id);

        Optional<Category> optCategory = categoryRepository.findById(id);
        if(optCategory.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION CHANGESTATEBYID CATEGORY -> Categoria encontrada con éxito");

        Category category = optCategory.get();
        category.setState(category.getState().equals("active") ? "inactive" : "active");
        category.setUpdateBy(updateBy);

        logger.info("ACCION CHANGESTATEBYID CATEGORY -> Actualizando estado");
        return categoryRepository.update(category);
    }

}
