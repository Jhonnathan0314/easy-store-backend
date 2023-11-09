package com.sophie.store.backend.context.purchase.model;

import com.sophie.store.backend.context.purchase.domain.model.Purchase;
import com.sophie.store.backend.context.purchase.data.PurchaseData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PurchaseTest {

    @InjectMocks
    private Purchase purchase;

    private static PurchaseData data;

    @BeforeAll
    static void setUp() {
        data = new PurchaseData();
    }

    @Test
    void isValidRequestTrue() {
        boolean response = purchase.isValid(data.getPurchaseGenerateValid());

        assertTrue(response);
    }

    @Test
    void isValidRequestFalseNull() {
        boolean response = purchase.isValid(data.getPurchaseGenerateInvalid());

        assertFalse(response);
    }

    @Test
    void isValidRequestFalseEmpty() {
        boolean response = purchase.isValid(data.getPurchaseEmpty());

        assertFalse(response);
    }

}