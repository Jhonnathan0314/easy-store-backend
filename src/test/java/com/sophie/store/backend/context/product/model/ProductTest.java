package com.sophie.store.backend.context.product.model;

import com.sophie.store.backend.context.product.data.ProductData;
import com.sophie.store.backend.context.product.domain.model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ProductTest {

    @InjectMocks
    private Product product;

    private static ProductData data;

    @BeforeAll
    static void setUp() {
        data = new ProductData();
    }

    @Test
    void isValidRequestTrue() {
        boolean response = product.isValid(data.getProductCreateValid());

        assertTrue(response);
    }

    @Test
    void isValidRequestFalseNull() {
        boolean response = product.isValid(data.getProductCreateInvalid());

        assertFalse(response);
    }

    @Test
    void isValidRequestFalseEmpty() {
        boolean response = product.isValid(data.getProductEmpty());

        assertFalse(response);
    }

}