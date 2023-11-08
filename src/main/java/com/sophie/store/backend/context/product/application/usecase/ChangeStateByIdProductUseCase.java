package com.sophie.store.backend.context.product.application.usecase;

import com.sophie.store.backend.context.product.domain.model.Product;
import com.sophie.store.backend.context.product.domain.port.ProductRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangeStateByIdProductUseCase {

    private final ProductRepository productRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Product changeStateById(Long id) throws NonExistenceException {
        Optional<Product> optProduct = productRepository.findById(id);
        if(optProduct.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        Product product = optProduct.get();
        product.setState(product.getState().equals("active") ? "inactive" : "active");
        product = productRepository.update(product);
        return product;
    }

}
