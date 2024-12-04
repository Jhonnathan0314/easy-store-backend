package com.easy.store.backend.context.purchase.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PurchaseEntityTest {

    @InjectMocks
    private PurchaseEntity purchaseEntity;

    @Test
    void onCreateTest() {
        purchaseEntity.onCreate();

        assertEquals("0", purchaseEntity.getTotal().toPlainString());
    }

}