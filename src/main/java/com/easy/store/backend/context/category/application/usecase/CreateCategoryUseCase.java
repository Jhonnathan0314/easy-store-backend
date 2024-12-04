package com.easy.store.backend.context.category.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.DuplicatedException;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Category create(Category category) throws DuplicatedException, NoIdReceivedException, InvalidBodyException {

        if(!category.isValid(category)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        if(category.getUser().getId() == null || category.getAccount().getId() == null)
            throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);

        if(categoryRepository.findByName(category.getName()).isPresent()) throw new DuplicatedException(errorMessages.DUPLICATED);

        return categoryRepository.create(category);
    }

}
