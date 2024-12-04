package com.easy.store.backend.security.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TestControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private TestController testController;

    @Test
    void welcomeTest() {
        String response = testController.welcome();

        assertNotNull(response);
        assertEquals(response, "Welcome to a secure endpoint");
    }

}