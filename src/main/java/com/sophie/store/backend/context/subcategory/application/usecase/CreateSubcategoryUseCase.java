package com.sophie.store.backend.context.subcategory.application.usecase;

import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.category.domain.port.CategoryRepository;
import com.sophie.store.backend.context.subcategory.domain.model.Subcategory;
import com.sophie.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.DuplicatedException;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateSubcategoryUseCase {

    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Subcategory create(Subcategory subcategory, Long idCategory) throws NoResultsException, DuplicatedException, InvalidBodyException {

        Optional<Category> optCategory = categoryRepository.findById(idCategory);
        if(optCategory.isEmpty()) throw new NoResultsException(errorMessages.NO_CATEGORY_RESULTS);

        subcategory.setCategory(Category.builder().id(idCategory).build());

        if(!subcategory.isValid(subcategory)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        if(subcategoryRepository.findByName(subcategory.getName()).isPresent()) throw new DuplicatedException(errorMessages.DUPLICATED);

        return subcategoryRepository.create(subcategory);
    }

}
