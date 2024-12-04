package com.easy.store.backend.context.category.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryEntityTest {

    @InjectMocks
    private CategoryEntity categoryEntity;

    @Test
    void onCreateTest() {
        categoryEntity.onCreate();

        assertEquals("active", categoryEntity.getState());
    }

}