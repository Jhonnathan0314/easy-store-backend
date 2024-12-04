package com.easy.store.backend.context.purchase_has_product.model;

import com.easy.store.backend.context.purchase_has_product.data.PurchaseHasProductData;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PurchaseHasProductTest {

    @InjectMocks
    private PurchaseHasProduct purchaseHasProduct;

    private static PurchaseHasProductData data;

    @BeforeAll
    static void setUp() {
        data = new PurchaseHasProductData();
    }

    @Test
    void isValidRequestTrue() {
        boolean response = purchaseHasProduct.isValid(data.getPurchaseHasProductAddValid());

        assertTrue(response);
    }

    @Test
    void isValidRequestFalseNull() {
        boolean response = purchaseHasProduct.isValid(data.getPurchaseHasProductAddInvalid());

        assertFalse(response);
    }

    @Test
    void isValidRequestFalseEmpty() {
        boolean response = purchaseHasProduct.isValid(data.getPurchaseHasProductEmpty());

        assertFalse(response);
    }

}