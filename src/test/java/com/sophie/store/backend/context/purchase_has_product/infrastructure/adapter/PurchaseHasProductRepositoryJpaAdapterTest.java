package com.sophie.store.backend.context.purchase_has_product.infrastructure.adapter;

import com.sophie.store.backend.context.purchase_has_product.data.PurchaseHasProductData;
import com.sophie.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.sophie.store.backend.context.purchase_has_product.infrastructure.mappers.PurchaseHasProductMapper;
import com.sophie.store.backend.context.purchase_has_product.infrastructure.persistence.PurchaseHasProductEntity;
import com.sophie.store.backend.context.purchase_has_product.infrastructure.persistence.PurchaseHasProductJpaRepository;
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
class PurchaseHasProductRepositoryJpaAdapterTest {

    @InjectMocks
    private PurchaseHasProductRepositoryJpaAdapter purchaseHasProductRepositoryJpaAdapter;

    @Mock
    private PurchaseHasProductJpaRepository purchaseHasProductJpaRepository;

    private static PurchaseHasProductMapper purchaseHasProductMapper;
    private static PurchaseHasProductData purchaseHasProductData;

    @BeforeAll
    static void setUp() {
        purchaseHasProductData = new PurchaseHasProductData();
        purchaseHasProductMapper = new PurchaseHasProductMapper();
    }

    @Test
    @Order(0)
    void findByIdTest() {
        PurchaseHasProductEntity mockEntity = purchaseHasProductMapper.modelToEntity(purchaseHasProductData.getPurchaseHasProductResponseOne());
        when(purchaseHasProductJpaRepository.findById(any(Long.class))).thenReturn(Optional.of(mockEntity));

        PurchaseHasProduct response = purchaseHasProductRepositoryJpaAdapter.findById(1L).orElse(null);

        assertNotNull(response);
        assertEquals(response, purchaseHasProductData.getPurchaseHasProductResponseOne());

        verify(purchaseHasProductJpaRepository).findById(any(Long.class));
    }

    @Test
    @Order(1)
    void findAllByProductIdTest() {
        List<PurchaseHasProductEntity> mockEntity = purchaseHasProductMapper.modelsToEntities(purchaseHasProductData.getPurchaseHasProductsList());
        when(purchaseHasProductJpaRepository.findAllByProductId(any(Long.class))).thenReturn(mockEntity);

        List<PurchaseHasProduct> response = purchaseHasProductRepositoryJpaAdapter.findAllByProductId(1L);

        assertNotNull(response);
        assertEquals(response.size(), purchaseHasProductData.getPurchaseHasProductsList().size());

        verify(purchaseHasProductJpaRepository).findAllByProductId(any(Long.class));
    }

    @Test
    @Order(2)
    void findAllByPurchaseIdTest() {
        List<PurchaseHasProductEntity> mockEntity = purchaseHasProductMapper.modelsToEntities(purchaseHasProductData.getPurchaseHasProductsList());
        when(purchaseHasProductJpaRepository.findAllByPurchaseId(any(Long.class))).thenReturn(mockEntity);

        List<PurchaseHasProduct> response = purchaseHasProductRepositoryJpaAdapter.findAllByPurchaseId(1L);

        assertNotNull(response);
        assertEquals(response.size(), purchaseHasProductData.getPurchaseHasProductsList().size());

        verify(purchaseHasProductJpaRepository).findAllByPurchaseId(any(Long.class));
    }

    @Test
    @Order(3)
    void addTest() {
        PurchaseHasProductEntity mockEntity = purchaseHasProductMapper.modelToEntity(purchaseHasProductData.getPurchaseHasProductResponseOne());
        when(purchaseHasProductJpaRepository.save(any(PurchaseHasProductEntity.class))).thenReturn(mockEntity);

        PurchaseHasProduct response = purchaseHasProductRepositoryJpaAdapter.add(purchaseHasProductData.getPurchaseHasProductAddValid());

        assertNotNull(response);
        assertEquals(response, purchaseHasProductData.getPurchaseHasProductResponseOne());

        verify(purchaseHasProductJpaRepository).save(any(PurchaseHasProductEntity.class));
    }

    @Test
    @Order(4)
    void removeByIdTest() {
        purchaseHasProductRepositoryJpaAdapter.removeById(1L);

        verify(purchaseHasProductJpaRepository).deleteById(any(Long.class));
    }
}