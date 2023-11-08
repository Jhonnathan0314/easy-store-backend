package com.sophie.store.backend.context.subcategory.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SubcategoryEntityTest {

    @InjectMocks
    private SubcategoryEntity subcategoryEntity;

    @Test
    void onCreateTest() {
        subcategoryEntity.onCreate();

        assertEquals("active", subcategoryEntity.getState());
    }

}