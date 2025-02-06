package com.easy.store.backend.context.product.infrastructure.adapter;

import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
import com.easy.store.backend.context.product.infrastructure.mappers.ProductCreateMapper;
import com.easy.store.backend.context.product.infrastructure.mappers.ProductMapper;
import com.easy.store.backend.context.product.infrastructure.mappers.ProductResponseMapper;
import com.easy.store.backend.context.product.infrastructure.mappers.ProductUpdateMapper;
import com.easy.store.backend.context.product.infrastructure.persistence.ProductEntity;
import com.easy.store.backend.context.product.infrastructure.persistence.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryJpaAdapter implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductCreateMapper createMapper = new ProductCreateMapper();
    private final ProductUpdateMapper updateMapper = new ProductUpdateMapper();
    private final ProductResponseMapper responseMapper = new ProductResponseMapper();

    @Override
    public List<Product> findAll() {
        List<ProductEntity> productEntities = productJpaRepository.findAll();
        return responseMapper.entitiesToModels(productEntities);
    }

    @Override
    public Optional<Product> findById(Long id) {
        Optional<ProductEntity> optProductEntity = productJpaRepository.findById(id);
        return optProductEntity.map(responseMapper::entityToModel);
    }

    @Override
    public Optional<Product> findByName(String name) {
        Optional<ProductEntity> optProductEntity = productJpaRepository.findByName(name);
        return optProductEntity.map(responseMapper::entityToModel);
    }

    @Override
    public List<Product> findByAccountId(Long accountId) {
        List<ProductEntity> productEntities = productJpaRepository.findByAccountId(accountId);
        return responseMapper.entitiesToModels(productEntities);
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        List<ProductEntity> productEntities = productJpaRepository.findByCategoryId(categoryId);
        return responseMapper.entitiesToModels(productEntities);
    }

    @Override
    public List<Product> findBySubcategoryId(Long subcategoryId) {
        List<ProductEntity> productEntities = productJpaRepository.findBySubcategoryId(subcategoryId);
        return responseMapper.entitiesToModels(productEntities);
    }

    @Override
    public Product create(Product product) {
        ProductEntity productEntity = productJpaRepository.save(createMapper.modelToEntity(product));
        return responseMapper.entityToModel(productEntity);
    }

    @Override
    public Product update(Product product) {
        ProductEntity productEntity = productJpaRepository.save(updateMapper.modelToEntity(product));
        return responseMapper.entityToModel(productEntity);
    }

    @Override
    public void deleteById(Long id) { productJpaRepository.deleteById(id); }
}
