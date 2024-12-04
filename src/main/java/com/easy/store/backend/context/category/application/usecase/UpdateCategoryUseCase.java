package com.easy.store.backend.context.category.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoChangesException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateCategoryUseCase {

    private final ErrorMessages errorMessages = new ErrorMessages();
    private final CategoryRepository categoryRepository;

    public Category update(Category category) throws NoIdReceivedException, NoResultsException, NoChangesException, InvalidBodyException {

        if(category.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);

        if(!category.isValid(category)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        if(category.getUser().getId() == null || category.getAccount().getId() == null)
            throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);

        Optional<Category> optCategory = categoryRepository.findById(category.getId());
        if(optCategory.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);

        Category categoryDb = optCategory.get();
        if(categoryDb.getName().equals(category.getName())) throw new NoChangesException(errorMessages.NO_CHANGES);

        category.setState(categoryDb.getState());

        return categoryRepository.update(category);
    }

}
