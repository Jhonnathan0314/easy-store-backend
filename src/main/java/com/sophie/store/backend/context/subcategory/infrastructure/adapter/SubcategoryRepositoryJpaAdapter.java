package com.sophie.store.backend.context.subcategory.infrastructure.adapter;

import com.sophie.store.backend.context.subcategory.domain.model.Subcategory;
import com.sophie.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.sophie.store.backend.context.subcategory.infrastructure.mappers.SubcategoryMapper;
import com.sophie.store.backend.context.subcategory.infrastructure.persistence.SubcategoryEntity;
import com.sophie.store.backend.context.subcategory.infrastructure.persistence.SubcategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SubcategoryRepositoryJpaAdapter implements SubcategoryRepository {

    private final SubcategoryJpaRepository subcategoryJpaRepository;
    private final SubcategoryMapper mapper = new SubcategoryMapper();

    @Override
    public List<Subcategory> findAll() {
        List<SubcategoryEntity> subcategoryEntities = subcategoryJpaRepository.findAll();
        return mapper.entitiesToModels(subcategoryEntities);
    }

    @Override
    public Optional<Subcategory> findById(Long id) {
        Optional<SubcategoryEntity> optSubcategoryEntity = subcategoryJpaRepository.findById(id);
        return optSubcategoryEntity.map(mapper::entityToModel);
    }

    @Override
    public Optional<Subcategory> findByName(String name) {
        Optional<SubcategoryEntity> optSubcategoryEntity = subcategoryJpaRepository.findByName(name);
        return optSubcategoryEntity.map(mapper::entityToModel);
    }

    @Override
    public Subcategory create(Subcategory subcategory) {
        SubcategoryEntity subcategoryEntity = subcategoryJpaRepository.save(mapper.modelToEntity(subcategory));
        return mapper.entityToModel(subcategoryEntity);
    }

    @Override
    public Subcategory update(Subcategory subcategory) {
        SubcategoryEntity subcategoryEntity = subcategoryJpaRepository.save(mapper.modelToEntity(subcategory));
        return mapper.entityToModel(subcategoryEntity);
    }

    @Override
    public void deleteById(Long id) { subcategoryJpaRepository.deleteById(id); }
}
