package com.sophie.store.backend.context.payment_type.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentTypeEntityTest {

    @InjectMocks
    private PaymentTypeEntity paymentTypeEntity;

    @Test
    void onCreateTest() {
        paymentTypeEntity.onCreate();

        assertEquals("active", paymentTypeEntity.getState());
    }

}