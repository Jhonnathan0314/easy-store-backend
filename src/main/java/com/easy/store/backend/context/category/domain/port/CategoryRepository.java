package com.easy.store.backend.context.category.domain.port;

import com.easy.store.backend.context.category.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    List<Category> findByAccountId(Long accountId);
    Optional<Category> findByName(String name);
    Category create(Category category);
    Category update(Category category);
    void deleteById(Long id);
}
