package com.sophie.store.backend.context.product.application.usecase;

import com.sophie.store.backend.context.product.domain.model.Product;
import com.sophie.store.backend.context.product.domain.port.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindByNameProductUseCase {

    private final ProductRepository productRepository;

    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

}
