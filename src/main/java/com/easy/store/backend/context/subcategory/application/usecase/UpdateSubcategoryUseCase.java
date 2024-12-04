package com.easy.store.backend.context.subcategory.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoChangesException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateSubcategoryUseCase {

    private final ErrorMessages errorMessages = new ErrorMessages();
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    public Subcategory update(Subcategory subcategory, Long idCategory) throws NoIdReceivedException, NoResultsException, NoChangesException, InvalidBodyException {

        Optional<Category> optCategory = categoryRepository.findById(idCategory);
        if(optCategory.isEmpty()) throw new NoResultsException(errorMessages.NO_CATEGORY_RESULTS);

        subcategory.setCategory(optCategory.get());

        if(subcategory.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);

        if(!subcategory.isValid(subcategory)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        Optional<Subcategory> optSubcategory = subcategoryRepository.findById(subcategory.getId());
        if(optSubcategory.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);

        Subcategory subcategoryDb = optSubcategory.get();
        if(Objects.equals(subcategoryDb.getCategory().getId(), subcategory.getCategory().getId()) &&
                subcategoryDb.getName().equals(subcategory.getName())) {
            throw new NoChangesException(errorMessages.NO_CHANGES);
        }

        subcategory.setState(subcategoryDb.getState());

        return subcategoryRepository.update(subcategory);
    }

}
