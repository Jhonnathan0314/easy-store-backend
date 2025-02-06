package com.easy.store.backend.context.subcategory.application.usecase;

import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindByAccountIdSubcategoryUseCase {

    private final SubcategoryRepository subcategoryRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<Subcategory> findByAccountId(Long accountId) throws NoResultsException {
        List<Subcategory> subcategories = subcategoryRepository.findByAccountId(accountId);
        if(subcategories == null || subcategories.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        return subcategories;
    }

}
