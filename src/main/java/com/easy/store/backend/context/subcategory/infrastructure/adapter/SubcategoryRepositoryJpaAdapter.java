package com.easy.store.backend.context.subcategory.infrastructure.adapter;

import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.easy.store.backend.context.subcategory.infrastructure.mappers.SubcategoryCreateMapper;
import com.easy.store.backend.context.subcategory.infrastructure.mappers.SubcategoryResponseMapper;
import com.easy.store.backend.context.subcategory.infrastructure.mappers.SubcategoryUpdateMapper;
import com.easy.store.backend.context.subcategory.infrastructure.persistence.SubcategoryEntity;
import com.easy.store.backend.context.subcategory.infrastructure.persistence.SubcategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SubcategoryRepositoryJpaAdapter implements SubcategoryRepository {

    private final SubcategoryJpaRepository subcategoryJpaRepository;
    private final SubcategoryResponseMapper responseMapper = new SubcategoryResponseMapper();
    private final SubcategoryCreateMapper createMapper = new SubcategoryCreateMapper();
    private final SubcategoryUpdateMapper updateMapper = new SubcategoryUpdateMapper();

    @Override
    public List<Subcategory> findAll() {
        List<SubcategoryEntity> subcategoryEntities = subcategoryJpaRepository.findAll();
        return responseMapper.entitiesToModels(subcategoryEntities);
    }

    @Override
    public Optional<Subcategory> findById(Long id) {
        Optional<SubcategoryEntity> optSubcategoryEntity = subcategoryJpaRepository.findById(id);
        return optSubcategoryEntity.map(responseMapper::entityToModel);
    }

    @Override
    public Optional<Subcategory> findByName(String name) {
        Optional<SubcategoryEntity> optSubcategoryEntity = subcategoryJpaRepository.findByName(name);
        return optSubcategoryEntity.map(responseMapper::entityToModel);
    }

    @Override
    public List<Subcategory> findByAccountId(Long accountId) {
        List<SubcategoryEntity> subcategoryEntities = subcategoryJpaRepository.findByAccountId(accountId);
        return responseMapper.entitiesToModels(subcategoryEntities);
    }

    @Override
    public List<Subcategory> findByCategoryId(Long categoryId) {
        List<SubcategoryEntity> subcategoryEntities = subcategoryJpaRepository.findByCategoryId(categoryId);
        return responseMapper.entitiesToModels(subcategoryEntities);
    }

    @Override
    public Subcategory create(Subcategory subcategory) {
        SubcategoryEntity subcategoryEntity = subcategoryJpaRepository.save(createMapper.modelToEntity(subcategory));
        return responseMapper.entityToModel(subcategoryEntity);
    }

    @Override
    public Subcategory update(Subcategory subcategory) {
        SubcategoryEntity subcategoryEntity = subcategoryJpaRepository.save(updateMapper.modelToEntity(subcategory));
        return responseMapper.entityToModel(subcategoryEntity);
    }

    @Override
    public void deleteById(Long id) { subcategoryJpaRepository.deleteById(id); }
}
