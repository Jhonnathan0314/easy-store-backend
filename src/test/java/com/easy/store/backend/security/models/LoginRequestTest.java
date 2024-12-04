package com.easy.store.backend.security.models;

import com.easy.store.backend.security.data.SecurityData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoginRequestTest {

    @InjectMocks
    private LoginRequest loginRequest;

    private static SecurityData data;

    @BeforeAll
    static void setUp() {
        data = new SecurityData();
    }

    @Test
    void isValidRequestTrue() {
        boolean response = loginRequest.isValidRequest(data.getLoginRequest());

        assertTrue(response);
    }

    @Test
    void isValidRequestFalseNull() {
        boolean response = loginRequest.isValidRequest(data.getBadLoginRequest());

        assertFalse(response);
    }

    @Test
    void isValidRequestFalseEmpty() {
        boolean response = loginRequest.isValidRequest(data.getEmptyLoginRequest());

        assertFalse(response);
    }

}