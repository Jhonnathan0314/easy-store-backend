package com.sophie.store.backend.context.product.infrastructure.adapter;

import com.sophie.store.backend.context.product.domain.model.Product;
import com.sophie.store.backend.context.product.domain.port.ProductRepository;
import com.sophie.store.backend.context.product.infrastructure.mappers.ProductMapper;
import com.sophie.store.backend.context.product.infrastructure.persistence.ProductEntity;
import com.sophie.store.backend.context.product.infrastructure.persistence.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductMapper mapper = new ProductMapper();

    @Override
    public List<Product> findAll() {
        List<ProductEntity> productEntities = productJpaRepository.findAll();
        return mapper.entitiesToModels(productEntities);
    }

    @Override
    public Optional<Product> findById(Long id) {
        Optional<ProductEntity> optProductEntity = productJpaRepository.findById(id);
        return optProductEntity.map(mapper::entityToModel);
    }

    @Override
    public Optional<Product> findByName(String name) {
        Optional<ProductEntity> optProductEntity = productJpaRepository.findByName(name);
        return optProductEntity.map(mapper::entityToModel);
    }

    @Override
    public Product create(Product product) {
        ProductEntity productEntity = productJpaRepository.save(mapper.modelToEntity(product));
        return mapper.entityToModel(productEntity);
    }

    @Override
    public Product update(Product product) {
        ProductEntity productEntity = productJpaRepository.save(mapper.modelToEntity(product));
        return mapper.entityToModel(productEntity);
    }

    @Override
    public void deleteById(Long id) { productJpaRepository.deleteById(id); }
}
