package com.sophie.store.backend.context.subcategory.application.usecase;

import com.sophie.store.backend.context.subcategory.domain.model.Subcategory;
import com.sophie.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeleteByIdSubcategoryUseCase {

    private final SubcategoryRepository subcategoryRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public void deleteById(Long id) throws NonExistenceException {
        Optional<Subcategory> subcategory = subcategoryRepository.findById(id);
        if(subcategory.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        subcategoryRepository.deleteById(id);
    }

}
