package com.sophie.store.backend.context.category.infrastructure.adapter;

import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.category.domain.port.CategoryRepository;
import com.sophie.store.backend.context.category.infrastructure.mappers.CategoryMapper;
import com.sophie.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.sophie.store.backend.context.category.infrastructure.persistence.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryJpaAdapter implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;
    private final CategoryMapper mapper = new CategoryMapper();

    @Override
    public List<Category> findAll() {
        List<CategoryEntity> categoryEntities = categoryJpaRepository.findAll();
        return mapper.entitiesToModels(categoryEntities);
    }

    @Override
    public Optional<Category> findById(Long id) {
        Optional<CategoryEntity> optCategoryEntity = categoryJpaRepository.findById(id);
        return optCategoryEntity.map(mapper::entityToModel);
    }

    @Override
    public Optional<Category> findByName(String name) {
        Optional<CategoryEntity> optCategoryEntity = categoryJpaRepository.findByName(name);
        return optCategoryEntity.map(mapper::entityToModel);
    }

    @Override
    public Category create(Category category) {
        CategoryEntity categoryEntity = categoryJpaRepository.save(mapper.modelToEntity(category));
        return mapper.entityToModel(categoryEntity);
    }

    @Override
    public Category update(Category category) {
        CategoryEntity categoryEntity = categoryJpaRepository.save(mapper.modelToEntity(category));
        return mapper.entityToModel(categoryEntity);
    }

    @Override
    public void deleteById(Long id) { categoryJpaRepository.deleteById(id); }
}
