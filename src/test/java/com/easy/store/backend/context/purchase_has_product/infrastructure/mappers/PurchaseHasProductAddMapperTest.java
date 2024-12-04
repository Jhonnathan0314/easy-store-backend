package com.easy.store.backend.context.purchase_has_product.infrastructure.mappers;

import com.easy.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductAddDTO;
import com.easy.store.backend.context.purchase_has_product.data.PurchaseHasProductData;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.infrastructure.persistence.PurchaseHasProductEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PurchaseHasProductAddMapperTest {

    @InjectMocks
    private PurchaseHasProductAddMapper purchaseHasProductAddMapper;

    private static PurchaseHasProductData purchaseHasProductData;

    @BeforeAll
    static void setUp() {
        purchaseHasProductData = new PurchaseHasProductData();
    }

    @Test
    void entityToModelTest() {
        PurchaseHasProductEntity purchaseHasProductEntity = purchaseHasProductData.getPurchaseHasProductEntity();

        PurchaseHasProduct purchaseHasProductModel = purchaseHasProductAddMapper.entityToModel(purchaseHasProductEntity);

        assertNotNull(purchaseHasProductModel);
    }

    @Test
    void modelToEntityTest() {
        PurchaseHasProduct purchaseHasProductModel = purchaseHasProductData.getPurchaseHasProductModel();

        PurchaseHasProductEntity purchaseHasProductEntity = purchaseHasProductAddMapper.modelToEntity(purchaseHasProductModel);

        assertNotNull(purchaseHasProductEntity);
    }

    @Test
    void modelToDtoTest() {
        PurchaseHasProduct purchaseHasProductModel = purchaseHasProductData.getPurchaseHasProductModel();

        PurchaseHasProductAddDTO purchaseHasProductAddDTO = purchaseHasProductAddMapper.modelToDto(purchaseHasProductModel);

        assertNotNull(purchaseHasProductAddDTO);
    }

    @Test
    void dtoToModelTest() {
        PurchaseHasProductAddDTO purchaseHasProductAddDTO = purchaseHasProductData.getPurchaseHasProductAddDTO();

        PurchaseHasProduct purchaseHasProductModel = purchaseHasProductAddMapper.dtoToModel(purchaseHasProductAddDTO);

        assertNotNull(purchaseHasProductModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<PurchaseHasProductEntity> purchaseHasProductEntities = List.of(purchaseHasProductData.getPurchaseHasProductEntity());

        List<PurchaseHasProduct> purchaseHasProductModels = purchaseHasProductAddMapper.entitiesToModels(purchaseHasProductEntities);

        assertNotNull(purchaseHasProductModels);
        assertEquals(purchaseHasProductEntities.size(), purchaseHasProductModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<PurchaseHasProduct> purchaseHasProductModels = List.of(purchaseHasProductData.getPurchaseHasProductModel());

        List<PurchaseHasProductEntity> purchaseHasProductEntities = purchaseHasProductAddMapper.modelsToEntities(purchaseHasProductModels);

        assertNotNull(purchaseHasProductEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<PurchaseHasProduct> purchaseHasProductModels = List.of(purchaseHasProductData.getPurchaseHasProductModel());

        List<PurchaseHasProductAddDTO> purchaseHasProductAddDTOs = purchaseHasProductAddMapper.modelsToDtos(purchaseHasProductModels);

        assertNotNull(purchaseHasProductAddDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<PurchaseHasProductAddDTO> purchaseHasProductAddDTOs = List.of(purchaseHasProductData.getPurchaseHasProductAddDTO());

        List<PurchaseHasProduct> purchaseHasProductModels = purchaseHasProductAddMapper.dtosToModels(purchaseHasProductAddDTOs);

        assertNotNull(purchaseHasProductModels);
    }

}