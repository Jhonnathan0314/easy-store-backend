package com.sophie.store.backend.context.product.infrastructure.adapter;

import com.sophie.store.backend.context.product.data.ProductData;
import com.sophie.store.backend.context.product.domain.model.Product;
import com.sophie.store.backend.context.product.infrastructure.mappers.ProductMapper;
import com.sophie.store.backend.context.product.infrastructure.persistence.ProductEntity;
import com.sophie.store.backend.context.product.infrastructure.persistence.ProductJpaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryJpaAdapterTest {

    @InjectMocks
    private ProductRepositoryJpaAdapter productRepositoryJpaAdapter;

    @Mock
    private ProductJpaRepository productJpaRepository;

    private static ProductMapper productMapper;
    private static ProductData productData;

    @BeforeAll
    static void setUp() {
        productData = new ProductData();
        productMapper = new ProductMapper();
    }

    @Test
    @Order(0)
    void findAllTest() {
        List<ProductEntity> mockEntities = productMapper.modelsToEntities(productData.getProductsList());
        when(productJpaRepository.findAll()).thenReturn(mockEntities);

        List<Product> response = productRepositoryJpaAdapter.findAll();

        assertNotNull(response);
        assertEquals(mockEntities.size(), response.size());

        verify(productJpaRepository).findAll();
    }

    @Test
    @Order(1)
    void findByIdTest() {
        ProductEntity mockEntity = productMapper.modelToEntity(productData.getProductResponseOne());
        when(productJpaRepository.findById(any(Long.class))).thenReturn(Optional.of(mockEntity));

        Product response = productRepositoryJpaAdapter.findById(1L).orElse(null);

        assertNotNull(response);
        assertEquals(response, productData.getProductResponseOne());

        verify(productJpaRepository).findById(any(Long.class));
    }

    @Test
    @Order(2)
    void findByNameTest() {
        ProductEntity mockEntity = productMapper.modelToEntity(productData.getProductResponseOne());
        when(productJpaRepository.findByName(any(String.class))).thenReturn(Optional.of(mockEntity));

        Product response = productRepositoryJpaAdapter.findByName("test").orElse(null);

        assertNotNull(response);
        assertEquals(response, productData.getProductResponseOne());

        verify(productJpaRepository).findByName(any(String.class));
    }

    @Test
    @Order(3)
    void createTest() {
        ProductEntity mockEntity = productMapper.modelToEntity(productData.getProductResponseOne());
        when(productJpaRepository.save(any(ProductEntity.class))).thenReturn(mockEntity);

        Product response = productRepositoryJpaAdapter.create(productData.getProductCreateValid());

        assertNotNull(response);
        assertEquals(response, productData.getProductResponseOne());

        verify(productJpaRepository).save(any(ProductEntity.class));
    }

    @Test
    @Order(4)
    void updateTest() {
        ProductEntity mockEntity = productMapper.modelToEntity(productData.getProductResponseOne());
        when(productJpaRepository.save(any(ProductEntity.class))).thenReturn(mockEntity);

        Product response = productRepositoryJpaAdapter.update(productData.getProductActive());

        assertNotNull(response);
        assertEquals(response, productData.getProductResponseOne());

        verify(productJpaRepository).save(any(ProductEntity.class));
    }

    @Test
    @Order(5)
    void deleteByIdTest() {
        productRepositoryJpaAdapter.deleteById(1L);

        verify(productJpaRepository).deleteById(any(Long.class));
    }
}