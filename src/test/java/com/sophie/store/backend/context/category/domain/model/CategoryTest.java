package com.sophie.store.backend.context.category.domain.model;

import com.sophie.store.backend.context.category.data.CategoryData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CategoryTest {

    @InjectMocks
    private Category role;

    private static CategoryData data;

    @BeforeAll
    static void setUp() {
        data = new CategoryData();
    }

    @Test
    void isValidRequestTrue() {
        boolean response = role.isValid(data.getCategoryCreateValid());

        assertTrue(response);
    }

    @Test
    void isValidRequestFalseNull() {
        boolean response = role.isValid(data.getCategoryCreateInvalid());

        assertFalse(response);
    }

    @Test
    void isValidRequestFalseEmpty() {
        boolean response = role.isValid(data.getCategoryEmpty());

        assertFalse(response);
    }

}