package com.sophie.store.backend.context.subcategory.domain.port;

import com.sophie.store.backend.context.subcategory.domain.model.Subcategory;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository {
    List<Subcategory> findAll();
    Optional<Subcategory> findById(Long id);
    Optional<Subcategory> findByName(String name);
    Subcategory create(Subcategory category);
    Subcategory update(Subcategory category);
    void deleteById(Long id);
}
