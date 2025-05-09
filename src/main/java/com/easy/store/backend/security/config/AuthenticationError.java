package com.easy.store.backend.security.config;

import com.easy.store.backend.utils.messages.ApiResponse;
import com.easy.store.backend.utils.messages.ErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationError implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code(HttpServletResponse.SC_UNAUTHORIZED)
                .title("Unauthorized")
                .detail("No estás autenticado. Por favor, inicia sesión.")
                .build();

        ApiResponse<ErrorMessage> apiResponse = new ApiResponse<>();
        apiResponse.setError(errorMessage);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }

}
