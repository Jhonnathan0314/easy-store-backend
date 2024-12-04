package com.easy.store.backend.context.product.infrastructure.mappers;

import com.easy.store.backend.context.product.application.dto.ProductCreateDTO;
import com.easy.store.backend.context.product.data.ProductData;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.infrastructure.persistence.ProductEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ProductCreateMapperTest {

    @InjectMocks
    private ProductCreateMapper productCreateMapper;

    private static ProductData productData;

    @BeforeAll
    static void setUp() {
        productData = new ProductData();
    }

    @Test
    void entityToModelTest() {
        ProductEntity productEntity = productData.getProductEntity();

        Product productModel = productCreateMapper.entityToModel(productEntity);

        assertNotNull(productModel);
    }

    @Test
    void modelToEntityTest() {
        Product productModel = productData.getProductModel();

        ProductEntity productEntity = productCreateMapper.modelToEntity(productModel);

        assertNotNull(productEntity);
    }

    @Test
    void modelToDtoTest() {
        Product productModel = productData.getProductModel();

        ProductCreateDTO productCreateDTO = productCreateMapper.modelToDto(productModel);

        assertNotNull(productCreateDTO);
    }

    @Test
    void dtoToModelTest() {
        ProductCreateDTO productCreateDTO = productData.getProductCreateDTO();

        Product productModel = productCreateMapper.dtoToModel(productCreateDTO);

        assertNotNull(productModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<ProductEntity> productEntities = List.of(productData.getProductEntity());

        List<Product> productModels = productCreateMapper.entitiesToModels(productEntities);

        assertNotNull(productModels);
        assertEquals(productEntities.size(), productModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<Product> productModels = List.of(productData.getProductModel());

        List<ProductEntity> productEntities = productCreateMapper.modelsToEntities(productModels);

        assertNotNull(productEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Product> productModels = List.of(productData.getProductModel());

        List<ProductCreateDTO> productCreateDTOs = productCreateMapper.modelsToDtos(productModels);

        assertNotNull(productCreateDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<ProductCreateDTO> productCreateDTOs = List.of(productData.getProductCreateDTO());

        List<Product> productModels = productCreateMapper.dtosToModels(productCreateDTOs);

        assertNotNull(productModels);
    }

}