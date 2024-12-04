package com.easy.store.backend.context.product.infrastructure.mappers;

import com.easy.store.backend.context.product.application.dto.ProductDTO;
import com.easy.store.backend.context.product.data.ProductData;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.infrastructure.persistence.ProductEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ProductMapperTest {

    @InjectMocks
    private ProductMapper productMapper;

    private static ProductData productData;

    @BeforeAll
    static void setUp() {
        productData = new ProductData();
    }

    @Test
    void entityToModelTest() {
        ProductEntity productEntity = productData.getProductEntity();

        Product productModel = productMapper.entityToModel(productEntity);

        assertNotNull(productModel);
    }

    @Test
    void modelToEntityTest() {
        Product productModel = productData.getProductModel();

        ProductEntity productEntity = productMapper.modelToEntity(productModel);

        assertNotNull(productEntity);
    }

    @Test
    void modelToDtoTest() {
        Product productModel = productData.getProductModel();

        ProductDTO productDTO = productMapper.modelToDto(productModel);

        assertNotNull(productDTO);
    }

    @Test
    void dtoToModelTest() {
        ProductDTO productDTO = productData.getProductDTO();

        Product productModel = productMapper.dtoToModel(productDTO);

        assertNotNull(productModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<ProductEntity> productEntities = List.of(productData.getProductEntity());

        List<Product> productModels = productMapper.entitiesToModels(productEntities);

        assertNotNull(productModels);
    }

    @Test
    void modelsToEntitiesTest() {
        List<Product> productModels = List.of(productData.getProductModel());

        List<ProductEntity> productEntities = productMapper.modelsToEntities(productModels);

        assertNotNull(productEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Product> productModels = List.of(productData.getProductModel());

        List<ProductDTO> productDTOs = productMapper.modelsToDtos(productModels);

        assertNotNull(productDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<ProductDTO> productDTOs = List.of(productData.getProductDTO());

        List<Product> productModels = productMapper.dtosToModels(productDTOs);

        assertNotNull(productModels);
    }

}