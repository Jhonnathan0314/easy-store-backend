package com.sophie.store.backend.context.category.application.usecase;

import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.category.domain.port.CategoryRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NonExisteceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangeStateByIdCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Category changeStateById(Long id) throws NonExisteceException {
        Optional<Category> optCategory = categoryRepository.findById(id);
        if(optCategory.isEmpty()) throw new NonExisteceException(errorMessages.NON_EXISTENT_DATA);
        Category category = optCategory.get();
        category.setState(category.getState().equals("active") ? "inactive" : "active");
        category = categoryRepository.update(category);
        return category;
    }

}
