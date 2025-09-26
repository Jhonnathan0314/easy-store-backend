package com.easy.store.backend.context.category_has_payment_type.infrastructure.adapter;

import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentType;
import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentTypeId;
import com.easy.store.backend.context.category_has_payment_type.domain.port.CategoryHasPaymentTypeRepository;
import com.easy.store.backend.context.category_has_payment_type.infrastructure.mapper.CategoryHasPaymentTypeCreateMapper;
import com.easy.store.backend.context.category_has_payment_type.infrastructure.mapper.CategoryHasPaymentTypeResponseMapper;
import com.easy.store.backend.context.category_has_payment_type.infrastructure.mapper.CategoryHasPaymentTypeUpdateMapper;
import com.easy.store.backend.context.category_has_payment_type.infrastructure.persistence.CategoryHasPaymentTypeEntity;
import com.easy.store.backend.context.category_has_payment_type.infrastructure.persistence.CategoryHasPaymentTypeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryHasPaymentTypeRepositoryJpaAdapter implements CategoryHasPaymentTypeRepository {

    private final CategoryHasPaymentTypeJpaRepository categoryHasPaymentTypeJpaRepository;

    private final CategoryHasPaymentTypeResponseMapper responseMapper = new CategoryHasPaymentTypeResponseMapper();
    private final CategoryHasPaymentTypeCreateMapper createMapper = new CategoryHasPaymentTypeCreateMapper();
    private final CategoryHasPaymentTypeUpdateMapper updateMapper = new CategoryHasPaymentTypeUpdateMapper();

    @Override
    public List<CategoryHasPaymentType> findAll() {
        List<CategoryHasPaymentTypeEntity> entities = categoryHasPaymentTypeJpaRepository.findAll();
        return responseMapper.entitiesToModels(entities);
    }

    @Override
    public List<CategoryHasPaymentType> findByAccountId(Long accountId) {
        List<CategoryHasPaymentTypeEntity> entities = categoryHasPaymentTypeJpaRepository.findByAccountId(accountId);
        return responseMapper.entitiesToModels(entities);
    }

    @Override
    public List<CategoryHasPaymentType> findByCategoryId(Long categoryId) {
        List<CategoryHasPaymentTypeEntity> entities = categoryHasPaymentTypeJpaRepository.findByCategoryId(categoryId);
        return responseMapper.entitiesToModels(entities);
    }

    @Override
    public Optional<CategoryHasPaymentType> findById(CategoryHasPaymentTypeId id) {
        Optional<CategoryHasPaymentTypeEntity> entity = categoryHasPaymentTypeJpaRepository.findById(id);
        return entity.map(responseMapper::entityToModel);
    }

    @Override
    public CategoryHasPaymentType create(CategoryHasPaymentType categoryHasPaymentType) {
        CategoryHasPaymentTypeEntity entity = createMapper.modelToEntity(categoryHasPaymentType);
        return responseMapper.entityToModel(categoryHasPaymentTypeJpaRepository.save(entity));
    }

    @Override
    public CategoryHasPaymentType update(CategoryHasPaymentType categoryHasPaymentType) {
        CategoryHasPaymentTypeEntity entity = updateMapper.modelToEntity(categoryHasPaymentType);
        return responseMapper.entityToModel(categoryHasPaymentTypeJpaRepository.save(entity));
    }

    @Override
    public void deleteById(CategoryHasPaymentTypeId id) {
        categoryHasPaymentTypeJpaRepository.deleteById(id);
    }

}
