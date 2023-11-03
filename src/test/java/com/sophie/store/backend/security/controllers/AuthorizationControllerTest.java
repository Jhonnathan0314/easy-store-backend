package com.sophie.store.backend.security.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sophie.store.backend.context.user.domain.model.User;
import com.sophie.store.backend.security.data.SecurityData;
import com.sophie.store.backend.security.models.AuthResponse;
import com.sophie.store.backend.security.models.LoginRequest;
import com.sophie.store.backend.security.service.AuthorizationService;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.DuplicatedException;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import com.sophie.store.backend.utils.messages.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthorizationControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AuthorizationController authorizationController;

    @Mock
    private AuthorizationService authService;

    private SecurityData data;
    private ErrorMessages errorMessages;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorizationController).build();
        data = new SecurityData();
        errorMessages = new ErrorMessages();
    }

    @Test
    @Order(0)
    void validLogin() throws Exception {
        LoginRequest loginRequest = data.getLoginRequest();
        AuthResponse expectedResponse = data.getTokenResponse();

        when(authService.login(any(LoginRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data.token").value(expectedResponse.getToken()));

        verify(authService).login(any(LoginRequest.class));
    }

    @Test
    @Order(1)
    void invalidLoginBadBody() throws Exception {
        LoginRequest loginRequest = data.getBadLoginRequest();
        ApiResponse<AuthResponse> expectedResponse = data.getErrorResponseInvalidBody();

        when(authService.login(any(LoginRequest.class)))
                .thenThrow(new InvalidBodyException(errorMessages.INVALID_BODY));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(authService).login(any(LoginRequest.class));
    }

    @Test
    @Order(2)
    void invalidLoginNoResults() throws Exception {
        LoginRequest loginRequest = data.getLoginRequest();
        ApiResponse<AuthResponse> expectedResponse = data.getErrorResponseNoResults();

        when(authService.login(any(LoginRequest.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(authService).login(any(LoginRequest.class));
    }

    @Test
    @Order(3)
    void validRegister() throws Exception {
        User registerRequest = data.getRegisterRequest();
        AuthResponse expectedResponse = data.getTokenResponse();

        when(authService.register(any(User.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data.token").value(expectedResponse.getToken()));

        verify(authService).register(any(User.class));
    }

    @Test
    @Order(4)
    void invalidRegisterBadBody() throws Exception {
        User registerRequest = data.getBadRegisterRequest();
        ApiResponse<AuthResponse> expectedResponse = data.getErrorResponseInvalidBody();

        when(authService.register(any(User.class))).thenThrow(new InvalidBodyException(errorMessages.INVALID_BODY));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(authService).register(any(User.class));
    }

    @Test
    @Order(5)
    void invalidRegisterDuplicated() throws Exception {
        User registerRequest = data.getBadRegisterRequest();
        ApiResponse<AuthResponse> expectedResponse = data.getErrorResponseDuplicated();

        when(authService.register(any(User.class))).thenThrow(new DuplicatedException(errorMessages.DUPLICATED));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(authService).register(any(User.class));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}