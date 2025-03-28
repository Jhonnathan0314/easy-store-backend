package com.easy.store.backend.security.config;

import com.easy.store.backend.utils.messages.ApiResponse;
import com.easy.store.backend.utils.messages.ErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthorizationError implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code(HttpServletResponse.SC_FORBIDDEN)
                .title("Forbidden")
                .detail("No tienes permisos para acceder a este recurso.")
                .build();

        ApiResponse<ErrorMessage> apiResponse = new ApiResponse<>();
        apiResponse.setError(errorMessage);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }
}
