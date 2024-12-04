package com.easy.store.backend.security.service;

import com.easy.store.backend.context.user.application.usecase.CreateUserUseCase;
import com.easy.store.backend.context.user.application.usecase.FindByUsernameUserUseCase;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.security.data.SecurityData;
import com.easy.store.backend.security.jwt.JwtService;
import com.easy.store.backend.security.models.AuthResponse;
import com.easy.store.backend.security.models.LoginRequest;
import com.easy.store.backend.utils.exceptions.DuplicatedException;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @InjectMocks
    private AuthorizationService authorizationService;

    @Mock
    private FindByUsernameUserUseCase findByUsernameUserUseCase;

    @Mock
    private CreateUserUseCase createUserUseCase;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    private LoginRequest loginRequest;
    private User user;
    private AuthResponse authResponse;
    private final SecurityData data = new SecurityData();

    @BeforeEach
    void setUp() {
        loginRequest = data.getLoginRequest();
        user = data.getRegisterRequest();
        authResponse = data.getTokenResponse();
    }

    @Test
    @Order(0)
    void testLogin() throws NoResultsException, InvalidBodyException {
        when(findByUsernameUserUseCase.findByUsername(any(String.class))).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(), any())).thenReturn(authResponse.getToken());

        AuthResponse response = authorizationService.login(loginRequest);

        assertNotNull(response);
        assertEquals(authResponse.getToken(), response.getToken());
        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    }

    @Test
    @Order(0)
    void testLoginFailedUsernameNotFoundException() throws NoResultsException, InvalidBodyException {
        when(findByUsernameUserUseCase.findByUsername(any(String.class))).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(), any())).thenReturn(authResponse.getToken());

        AuthResponse response = authorizationService.login(loginRequest);

        assertNotNull(response);
        assertEquals(authResponse.getToken(), response.getToken());
        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    }

    @Test
    void testRegister() throws InvalidBodyException, DuplicatedException {
        when(createUserUseCase.create(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(), any())).thenReturn(authResponse.getToken());

        AuthResponse response = authorizationService.register(user);

        assertNotNull(response);
        assertEquals(authResponse.getToken(), response.getToken());
    }
}