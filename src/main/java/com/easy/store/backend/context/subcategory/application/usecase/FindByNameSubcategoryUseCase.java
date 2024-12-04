package com.easy.store.backend.context.subcategory.application.usecase;

import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindByNameSubcategoryUseCase {

    private final SubcategoryRepository subcategoryRepository;

    public Optional<Subcategory> findByName(String name) {
        return subcategoryRepository.findByName(name);
    }

}
