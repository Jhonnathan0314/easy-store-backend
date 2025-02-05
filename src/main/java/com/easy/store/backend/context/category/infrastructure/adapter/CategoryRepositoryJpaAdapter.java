package com.easy.store.backend.context.category.infrastructure.adapter;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.category.infrastructure.mappers.CategoryCreateMapper;
import com.easy.store.backend.context.category.infrastructure.mappers.CategoryResponseMapper;
import com.easy.store.backend.context.category.infrastructure.mappers.CategoryUpdateMapper;
import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.easy.store.backend.context.category.infrastructure.persistence.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryJpaAdapter implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;
    private final CategoryResponseMapper responseMapper = new CategoryResponseMapper();
    private final CategoryCreateMapper createMapper = new CategoryCreateMapper();
    private final CategoryUpdateMapper updateMapper = new CategoryUpdateMapper();

    @Override
    public List<Category> findAll() {
        List<CategoryEntity> categoryEntities = categoryJpaRepository.findAll();
        return responseMapper.entitiesToModels(categoryEntities);
    }

    @Override
    public Optional<Category> findById(Long id) {
        Optional<CategoryEntity> optCategoryEntity = categoryJpaRepository.findById(id);
        return optCategoryEntity.map(responseMapper::entityToModel);
    }

    @Override
    public List<Category> findByUserIdAndAccountId(Long userId, Long accountId) {
        List<CategoryEntity> categoryEntities = categoryJpaRepository.findByUserIdAndAccountId(userId, accountId);
        return responseMapper.entitiesToModels(categoryEntities);
    }

    @Override
    public Optional<Category> findByName(String name) {
        Optional<CategoryEntity> optCategoryEntity = categoryJpaRepository.findByName(name);
        return optCategoryEntity.map(responseMapper::entityToModel);
    }

    @Override
    public Category create(Category category) {
        CategoryEntity categoryEntity = categoryJpaRepository.save(createMapper.modelToEntity(category));
        return responseMapper.entityToModel(categoryEntity);
    }

    @Override
    public Category update(Category category) {
        CategoryEntity categoryEntity = categoryJpaRepository.save(updateMapper.modelToEntity(category));
        return responseMapper.entityToModel(categoryEntity);
    }

    @Override
    public void deleteById(Long id) { categoryJpaRepository.deleteById(id); }
}
