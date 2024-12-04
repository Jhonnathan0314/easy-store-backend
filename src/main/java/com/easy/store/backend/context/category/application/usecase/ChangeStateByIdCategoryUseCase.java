package com.easy.store.backend.context.category.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangeStateByIdCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Category changeStateById(Long id) throws NonExistenceException {
        Optional<Category> optCategory = categoryRepository.findById(id);
        if(optCategory.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        Category category = optCategory.get();
        category.setState(category.getState().equals("active") ? "inactive" : "active");
        category = categoryRepository.update(category);
        return category;
    }

}
