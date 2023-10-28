package com.sophie.store.backend.context.subcategory.application.usecase;

import com.sophie.store.backend.context.subcategory.domain.model.Subcategory;
import com.sophie.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NonExisteceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangeStateByIdSubcategoryUseCase {

    private final SubcategoryRepository subcategoryRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Subcategory changeStateById(Long id) throws NonExisteceException {
        Optional<Subcategory> optSubcategory = subcategoryRepository.findById(id);
        if(optSubcategory.isEmpty()) throw new NonExisteceException(errorMessages.NON_EXISTENT_DATA);
        Subcategory subcategory = optSubcategory.get();
        subcategory.setState(subcategory.getState().equals("active") ? "inactive" : "active");
        subcategory = subcategoryRepository.update(subcategory);
        return subcategory;
    }

}
