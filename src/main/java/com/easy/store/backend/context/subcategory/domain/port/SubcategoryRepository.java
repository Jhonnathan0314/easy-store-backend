package com.easy.store.backend.context.subcategory.domain.port;

import com.easy.store.backend.context.subcategory.domain.model.Subcategory;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository {
    List<Subcategory> findAll();
    Optional<Subcategory> findById(Long id);
    Optional<Subcategory> findByName(String name);
    List<Subcategory> findByAccountId(Long accountId);
    List<Subcategory> findByCategoryId(Long categoryId);
    Subcategory create(Subcategory category);
    Subcategory update(Subcategory category);
    void deleteById(Long id);
}
