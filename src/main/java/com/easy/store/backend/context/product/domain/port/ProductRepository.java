package com.easy.store.backend.context.product.domain.port;

import com.easy.store.backend.context.product.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
    List<Product> findBySubcategoryId(Long subcategoryId);
    Product create(Product product);
    Product update(Product product);
    void deleteById(Long id);
}
