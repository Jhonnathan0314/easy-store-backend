package com.easy.store.backend.context.user.domain.model;

import com.easy.store.backend.context.user.data.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @InjectMocks
    private User user;

    private static UserData data;

    @BeforeAll
    static void setUp() {
        data = new UserData();
    }

    @Test
    void isValidRequestTrue() {
        boolean response = user.isValid(data.getUserCreateValid());

        assertTrue(response);
    }

    @Test
    void isValidRequestFalseNull() {
        boolean response = user.isValid(data.getUserCreateInvalid());

        assertFalse(response);
    }

    @Test
    void isValidRequestFalseEmpty() {
        boolean response = user.isValid(data.getUserEmpty());

        assertFalse(response);
    }

}