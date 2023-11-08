package com.sophie.store.backend.context.category.application.usecase;

import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.category.domain.port.CategoryRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeleteByIdCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public void deleteById(Long id) throws NonExistenceException {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        categoryRepository.deleteById(id);
    }

}
