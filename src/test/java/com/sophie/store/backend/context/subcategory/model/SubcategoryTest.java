package com.sophie.store.backend.context.subcategory.model;

import com.sophie.store.backend.context.subcategory.data.SubcategoryData;
import com.sophie.store.backend.context.subcategory.domain.model.Subcategory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class SubcategoryTest {

    @InjectMocks
    private Subcategory subcategory;

    private static SubcategoryData data;

    @BeforeAll
    static void setUp() {
        data = new SubcategoryData();
    }

    @Test
    void isValidRequestTrue() {
        boolean response = subcategory.isValid(data.getSubcategoryCreateValid());

        assertTrue(response);
    }

    @Test
    void isValidRequestFalseNull() {
        boolean response = subcategory.isValid(data.getSubcategoryCreateInvalid());

        assertFalse(response);
    }

    @Test
    void isValidRequestFalseEmpty() {
        boolean response = subcategory.isValid(data.getSubcategoryEmpty());

        assertFalse(response);
    }

}