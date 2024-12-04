package com.easy.store.backend.context.purchase.infrastructure.adapter;

import com.easy.store.backend.context.purchase.data.PurchaseData;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.infrastructure.mappers.PurchaseMapper;
import com.easy.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import com.easy.store.backend.context.purchase.infrastructure.persistence.PurchaseJpaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseRepositoryJpaAdapterTest {

    @InjectMocks
    private PurchaseRepositoryJpaAdapter purchaseRepositoryJpaAdapter;

    @Mock
    private PurchaseJpaRepository purchaseJpaRepository;

    private static PurchaseMapper purchaseMapper;
    private static PurchaseData purchaseData;

    @BeforeAll
    static void setUp() {
        purchaseData = new PurchaseData();
        purchaseMapper = new PurchaseMapper();
    }

    @Test
    @Order(0)
    void findAllTest() {
        List<PurchaseEntity> mockEntities = purchaseMapper.modelsToEntities(purchaseData.getPurchasesList());
        when(purchaseJpaRepository.findAll()).thenReturn(mockEntities);

        List<Purchase> response = purchaseRepositoryJpaAdapter.findAll();

        assertNotNull(response);
        assertEquals(mockEntities.size(), response.size());

        verify(purchaseJpaRepository).findAll();
    }

    @Test
    @Order(1)
    void findByIdTest() {
        PurchaseEntity mockEntity = purchaseMapper.modelToEntity(purchaseData.getPurchaseResponseOne());
        when(purchaseJpaRepository.findById(any(Long.class))).thenReturn(Optional.of(mockEntity));

        Purchase response = purchaseRepositoryJpaAdapter.findById(1L).orElse(null);

        assertNotNull(response);
        assertEquals(response, purchaseData.getPurchaseResponseOne());

        verify(purchaseJpaRepository).findById(any(Long.class));
    }

    @Test
    @Order(2)
    void findByUserIdTest() {
        List<PurchaseEntity> mockEntity = purchaseMapper.modelsToEntities(purchaseData.getPurchasesList());
        when(purchaseJpaRepository.findAllByUserId(any(Long.class))).thenReturn(mockEntity);

        List<Purchase> response = purchaseRepositoryJpaAdapter.findByUserId(1L);

        assertNotNull(response);
        assertEquals(response.size(), purchaseData.getPurchasesList().size());

        verify(purchaseJpaRepository).findAllByUserId(any(Long.class));
    }

    @Test
    @Order(3)
    void findByPaymentTypeIdTest() {
        List<PurchaseEntity> mockEntity = purchaseMapper.modelsToEntities(purchaseData.getPurchasesList());
        when(purchaseJpaRepository.findAllByPaymentTypeId(any(Long.class))).thenReturn(mockEntity);

        List<Purchase> response = purchaseRepositoryJpaAdapter.findByPaymentTypeId(1L);

        assertNotNull(response);
        assertEquals(response.size(), purchaseData.getPurchasesList().size());

        verify(purchaseJpaRepository).findAllByPaymentTypeId(any(Long.class));
    }

    @Test
    @Order(3)
    void findByDateTest() {
        List<PurchaseEntity> mockEntity = purchaseMapper.modelsToEntities(purchaseData.getPurchasesList());
        when(purchaseJpaRepository.findAllByDate(any(Timestamp.class))).thenReturn(mockEntity);

        List<Purchase> response = purchaseRepositoryJpaAdapter.findByDate(purchaseData.getPurchaseResponseOne().getDate());

        assertNotNull(response);
        assertEquals(response.size(), purchaseData.getPurchasesList().size());

        verify(purchaseJpaRepository).findAllByDate(any(Timestamp.class));
    }

    @Test
    @Order(3)
    void findByDateBetweenTest() {
        List<PurchaseEntity> mockEntity = purchaseMapper.modelsToEntities(purchaseData.getPurchasesList());
        when(purchaseJpaRepository.findByDateBetween(any(Timestamp.class), any(Timestamp.class))).thenReturn(mockEntity);

        List<Purchase> response = purchaseRepositoryJpaAdapter.findByDateBetween(purchaseData.getFromDate(), purchaseData.getToDate());

        assertNotNull(response);
        assertEquals(response.size(), purchaseData.getPurchasesList().size());

        verify(purchaseJpaRepository).findByDateBetween(any(Timestamp.class), any(Timestamp.class));
    }

    @Test
    @Order(3)
    void findByTotalBetweenTest() {
        List<PurchaseEntity> mockEntity = purchaseMapper.modelsToEntities(purchaseData.getPurchasesList());
        when(purchaseJpaRepository.findByTotalBetween(any(BigDecimal.class), any(BigDecimal.class))).thenReturn(mockEntity);

        List<Purchase> response = purchaseRepositoryJpaAdapter.findByTotalBetween(purchaseData.getFromTotal(), purchaseData.getToTotal());

        assertNotNull(response);
        assertEquals(response.size(), purchaseData.getPurchasesList().size());

        verify(purchaseJpaRepository).findByTotalBetween(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    @Order(2)
    void generateTest() {
        PurchaseEntity mockEntity = purchaseMapper.modelToEntity(purchaseData.getPurchaseResponseOne());
        when(purchaseJpaRepository.save(any(PurchaseEntity.class))).thenReturn(mockEntity);

        Purchase response = purchaseRepositoryJpaAdapter.generate(purchaseData.getPurchaseGenerateValid());

        assertNotNull(response);
        assertEquals(response, purchaseData.getPurchaseResponseOne());

        verify(purchaseJpaRepository).save(any(PurchaseEntity.class));
    }

    @Test
    @Order(3)
    void deleteByIdTest() {
        purchaseRepositoryJpaAdapter.deleteById(1L);

        verify(purchaseJpaRepository).deleteById(any(Long.class));
    }
}