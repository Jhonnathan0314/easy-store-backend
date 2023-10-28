package com.sophie.store.backend.context.category.application.usecase;

import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.category.domain.port.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindByNameCategoryUseCase {

    private final CategoryRepository categoryRepository;

    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

}
