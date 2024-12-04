package com.easy.store.backend.context.product.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductEntityTest {

    @InjectMocks
    private ProductEntity productEntity;

    @Test
    void onCreateTest() {
        productEntity.onCreate();

        assertEquals("active", productEntity.getState());
    }

}