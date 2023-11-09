package com.sophie.store.backend.context.product.infrastructure.mappers;

import com.sophie.store.backend.context.product.application.dto.ProductUpdateDTO;
import com.sophie.store.backend.context.product.data.ProductData;
import com.sophie.store.backend.context.product.domain.model.Product;
import com.sophie.store.backend.context.product.infrastructure.persistence.ProductEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ProductUpdateMapperTest {

    @InjectMocks
    private ProductUpdateMapper productUpdateMapper;

    private static ProductData productData;

    @BeforeAll
    static void setUp() {
        productData = new ProductData();
    }

    @Test
    void entityToModelTest() {
        ProductEntity productEntity = productData.getProductEntity();

        Product productModel = productUpdateMapper.entityToModel(productEntity);

        assertNotNull(productModel);
    }

    @Test
    void modelToEntityTest() {
        Product productModel = productData.getProductModel();

        ProductEntity productEntity = productUpdateMapper.modelToEntity(productModel);

        assertNotNull(productEntity);
    }

    @Test
    void modelToDtoTest() {
        Product productModel = productData.getProductModel();

        ProductUpdateDTO productUpdateDTO = productUpdateMapper.modelToDto(productModel);

        assertNotNull(productUpdateDTO);
    }

    @Test
    void dtoToModelTest() {
        ProductUpdateDTO productUpdateDTO = productData.getProductUpdateDTO();

        Product productModel = productUpdateMapper.dtoToModel(productUpdateDTO);

        assertNotNull(productModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<ProductEntity> productEntities = List.of(productData.getProductEntity());

        List<Product> productModels = productUpdateMapper.entitiesToModels(productEntities);

        assertNotNull(productModels);
        assertEquals(productEntities.size(), productModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<Product> productModels = List.of(productData.getProductModel());

        List<ProductEntity> productEntities = productUpdateMapper.modelsToEntities(productModels);

        assertNotNull(productEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Product> productModels = List.of(productData.getProductModel());

        List<ProductUpdateDTO> productUpdateDTOs = productUpdateMapper.modelsToDtos(productModels);

        assertNotNull(productUpdateDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<ProductUpdateDTO> productUpdateDTOs = List.of(productData.getProductUpdateDTO());

        List<Product> productModels = productUpdateMapper.dtosToModels(productUpdateDTOs);

        assertNotNull(productModels);
    }

}