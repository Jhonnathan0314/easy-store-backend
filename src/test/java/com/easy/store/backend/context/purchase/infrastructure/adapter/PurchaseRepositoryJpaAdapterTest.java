package com.easy.store.backend.context.purchase.infrastructure.adapter;

import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import com.easy.store.backend.context.purchase.infrastructure.persistence.PurchaseJpaRepository;
import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import com.easy.store.backend.context.purchase_has_product.infrastructure.persistence.PurchaseHasProductEntity;
import com.easy.store.backend.context.purchase_has_product.infrastructure.persistence.PurchaseHasProductJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseRepositoryJpaAdapterTest {

    @Mock
    private PurchaseJpaRepository purchaseJpaRepository;

    @Mock
    private PurchaseHasProductJpaRepository purchaseHasProductJpaRepository;

    @InjectMocks
    private PurchaseRepositoryJpaAdapter adapter;

    private PurchaseEntity buildPurchase(Long id) {
        return PurchaseEntity.builder()
                .id(id)
                .user(UserEntity.builder().id(1L).build())
                .paymentType(PaymentTypeEntity.builder().id(1L).build())
                .category(CategoryEntity.builder().id(1L).build())
                .total(BigDecimal.ZERO)
                .state("active")
                .build();
    }

    private PurchaseHasProductEntity buildLine(BigDecimal subtotal) {
        return PurchaseHasProductEntity.builder()
                .subtotal(subtotal)
                .build();
    }

    @Test
    void recalculateTotal_sumsSubtotalsAndPersists() {
        Long purchaseId = 10L;
        when(purchaseJpaRepository.findById(purchaseId)).thenReturn(Optional.of(buildPurchase(purchaseId)));
        when(purchaseHasProductJpaRepository.findByPurchaseId(purchaseId)).thenReturn(List.of(
                buildLine(new BigDecimal("1500.00")),
                buildLine(new BigDecimal("2500.50")),
                buildLine(new BigDecimal("1000.00"))
        ));
        when(purchaseJpaRepository.save(org.mockito.ArgumentMatchers.any(PurchaseEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Purchase result = adapter.recalculateTotal(purchaseId);

        ArgumentCaptor<PurchaseEntity> captor = ArgumentCaptor.forClass(PurchaseEntity.class);
        org.mockito.Mockito.verify(purchaseJpaRepository).save(captor.capture());

        assertThat(captor.getValue().getTotal()).isEqualByComparingTo(new BigDecimal("5000.50"));
        assertThat(result.getTotal()).isEqualByComparingTo(new BigDecimal("5000.50"));
    }

    @Test
    void recalculateTotal_withNoLines_setsZero() {
        Long purchaseId = 20L;
        when(purchaseJpaRepository.findById(purchaseId)).thenReturn(Optional.of(buildPurchase(purchaseId)));
        when(purchaseHasProductJpaRepository.findByPurchaseId(purchaseId)).thenReturn(List.of());
        when(purchaseJpaRepository.save(org.mockito.ArgumentMatchers.any(PurchaseEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Purchase result = adapter.recalculateTotal(purchaseId);

        assertThat(result.getTotal()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void recalculateTotal_ignoresNullSubtotals() {
        Long purchaseId = 30L;
        when(purchaseJpaRepository.findById(purchaseId)).thenReturn(Optional.of(buildPurchase(purchaseId)));
        when(purchaseHasProductJpaRepository.findByPurchaseId(purchaseId)).thenReturn(java.util.Arrays.asList(
                buildLine(new BigDecimal("999.99")),
                buildLine(null)
        ));
        when(purchaseJpaRepository.save(org.mockito.ArgumentMatchers.any(PurchaseEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Purchase result = adapter.recalculateTotal(purchaseId);

        assertThat(result.getTotal()).isEqualByComparingTo(new BigDecimal("999.99"));
    }
}
