package com.sophie.store.backend.security.jwt;

import com.sophie.store.backend.context.user.domain.model.User;
import com.sophie.store.backend.security.data.SecurityData;
import com.sophie.store.backend.security.models.LoginRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private static SecurityData securityData;

    @BeforeAll
    static void setUp() {
        securityData = new SecurityData();
    }

    @Test
    void generateValidTokenSuccess() {
        LoginRequest loginRequest = securityData.getLoginRequest();

        User user = User.builder()
                .username(loginRequest.getUsername())
                .password(loginRequest.getPassword())
                .build();

        Map<String, String> claims = new HashMap<>();

        String generatedToken = jwtService.generateToken(user, claims);

        boolean response = jwtService.isTokenValid(generatedToken, user);

        assertNotNull(generatedToken);
        assertFalse(generatedToken.isEmpty());
        assertTrue(response);
    }

}