package com.sophie.store.backend.context.product.application.usecase;

import com.sophie.store.backend.context.product.domain.model.Product;
import com.sophie.store.backend.context.product.domain.port.ProductRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NonExisteceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeleteByIdProductUseCase {

    private final ProductRepository productRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public void deleteById(Long id) throws NonExisteceException {
        Optional<Product> subcategory = productRepository.findById(id);
        if(subcategory.isEmpty()) throw new NonExisteceException(errorMessages.NON_EXISTENT_DATA);
        productRepository.deleteById(id);
    }

}
