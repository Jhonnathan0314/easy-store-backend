package com.easy.store.backend.context.payment_type.domain.model;

import com.easy.store.backend.context.payment_type.data.PaymentTypeData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PaymentTypeTest {

    @InjectMocks
    private PaymentType paymentType;

    private static PaymentTypeData data;

    @BeforeAll
    static void setUp() {
        data = new PaymentTypeData();
    }

    @Test
    void isValidRequestTrue() {
        boolean response = paymentType.isValid(data.getPaymentTypeCreateValid());

        assertTrue(response);
    }

    @Test
    void isValidRequestFalseNull() {
        boolean response = paymentType.isValid(data.getPaymentTypeCreateInvalid());

        assertFalse(response);
    }

    @Test
    void isValidRequestFalseEmpty() {
        boolean response = paymentType.isValid(data.getPaymentTypeEmpty());

        assertFalse(response);
    }

}