package com.easy.store.backend.context.purchase.infrastructure.mappers;

import com.easy.store.backend.context.purchase.application.dto.PurchaseDTO;
import com.easy.store.backend.context.purchase.data.PurchaseData;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PurchaseMapperTest {

    @InjectMocks
    private PurchaseMapper purchaseMapper;

    private static PurchaseData purchaseData;

    @BeforeAll
    static void setUp() {
        purchaseData = new PurchaseData();
    }

    @Test
    void entityToModelTest() {
        PurchaseEntity purchaseEntity = purchaseData.getPurchaseEntity();

        Purchase purchaseModel = purchaseMapper.entityToModel(purchaseEntity);

        assertNotNull(purchaseModel);
    }

    @Test
    void modelToEntityTest() {
        Purchase purchaseModel = purchaseData.getPurchaseModel();

        PurchaseEntity purchaseEntity = purchaseMapper.modelToEntity(purchaseModel);

        assertNotNull(purchaseEntity);
    }

    @Test
    void modelToDtoTest() {
        Purchase purchaseModel = purchaseData.getPurchaseModel();

        PurchaseDTO purchaseDTO = purchaseMapper.modelToDto(purchaseModel);

        assertNotNull(purchaseDTO);
    }

    @Test
    void dtoToModelTest() {
        PurchaseDTO purchaseDTO = purchaseData.getPurchaseDTO();

        Purchase purchaseModel = purchaseMapper.dtoToModel(purchaseDTO);

        assertNotNull(purchaseModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<PurchaseEntity> purchaseEntities = List.of(purchaseData.getPurchaseEntity());

        List<Purchase> purchaseModels = purchaseMapper.entitiesToModels(purchaseEntities);

        assertNotNull(purchaseModels);
    }

    @Test
    void modelsToEntitiesTest() {
        List<Purchase> purchaseModels = List.of(purchaseData.getPurchaseModel());

        List<PurchaseEntity> purchaseEntities = purchaseMapper.modelsToEntities(purchaseModels);

        assertNotNull(purchaseEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Purchase> purchaseModels = List.of(purchaseData.getPurchaseModel());

        List<PurchaseDTO> purchaseDTOs = purchaseMapper.modelsToDtos(purchaseModels);

        assertNotNull(purchaseDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<PurchaseDTO> purchaseDTOs = List.of(purchaseData.getPurchaseDTO());

        List<Purchase> purchaseModels = purchaseMapper.dtosToModels(purchaseDTOs);

        assertNotNull(purchaseModels);
    }

}