package com.sophie.store.backend.context.product.domain.port;

import com.sophie.store.backend.context.product.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
    Product create(Product product);
    Product update(Product product);
    void deleteById(Long id);
}
