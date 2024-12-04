package com.easy.store.backend.context.roles.domain.model;

import com.easy.store.backend.context.roles.data.RoleData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoleTest {

    @InjectMocks
    private Role role;

    private static RoleData data;

    @BeforeAll
    static void setUp() {
        data = new RoleData();
    }

    @Test
    void isValidRequestTrue() {
        boolean response = role.isValid(data.getRoleCreateValid());

        assertTrue(response);
    }

    @Test
    void isValidRequestFalseNull() {
        boolean response = role.isValid(data.getRoleCreateInvalid());

        assertFalse(response);
    }

    @Test
    void isValidRequestFalseEmpty() {
        boolean response = role.isValid(data.getRoleEmpty());

        assertFalse(response);
    }

}