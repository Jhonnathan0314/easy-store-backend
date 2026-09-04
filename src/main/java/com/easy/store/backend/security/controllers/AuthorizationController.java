package com.easy.store.backend.security.controllers;

import com.easy.store.backend.context.user.application.dto.UserCreateDTO;
import com.easy.store.backend.context.user.application.dto.UserResponseDTO;
import com.easy.store.backend.context.user.infrastructure.mappers.UserCreateMapper;
import com.easy.store.backend.context.user.infrastructure.mappers.UserResponseMapper;
import com.easy.store.backend.security.models.AuthResponse;
import com.easy.store.backend.security.models.LoginRequest;
import com.easy.store.backend.security.models.ResetPasswordRequest;
import com.easy.store.backend.security.ratelimit.RateLimiterService;
import com.easy.store.backend.security.service.AuthorizationService;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.*;
import com.easy.store.backend.utils.messages.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authService;
    private final RateLimiterService rateLimiterService;

    private final UserCreateMapper userCreateMapper = new UserCreateMapper();
    private final UserResponseMapper userResponseMapper = new UserResponseMapper();

    @PostMapping(value = "login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest) throws NoResultsException, InvalidBodyException, TooManyRequestsException {
        checkRateLimit("login", httpRequest);
        ApiResponse<AuthResponse> response = new ApiResponse<>();
        response.setData(authService.login(request));
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody UserCreateDTO request, HttpServletRequest httpRequest) throws NoResultsException, NoIdReceivedException, InvalidBodyException, NoChangesException, DuplicatedException, NonExistenceException, TooManyRequestsException {
        checkRateLimit("register", httpRequest);
        ApiResponse<AuthResponse> response = new ApiResponse<>();
        response.setData(authService.register(userCreateMapper.dtoToModel(request)));
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "reset-password")
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(@Valid @RequestBody ResetPasswordRequest request) throws NoResultsException, NoIdReceivedException, InvalidBodyException, NoChangesException, NonExistenceException {
        ApiResponse<UserResponseDTO> response = new ApiResponse<>();
        response.setData(userResponseMapper.modelToDto(authService.resetPassword(request)));
        return ResponseEntity.ok(response);
    }

    // Se limita por IP + accion (login/register) para frenar fuerza bruta de contraseñas y abuso
    // de registro masivo de cuentas. Se usa X-Forwarded-For si esta presente porque en despliegues
    // detras de un proxy/load balancer (ej. Railway) request.getRemoteAddr() devolveria la IP del
    // proxy y no la del cliente real, lo que haria que el limite aplique a todos los usuarios como
    // si fueran uno solo.
    private void checkRateLimit(String action, HttpServletRequest httpRequest) throws TooManyRequestsException {
        String clientIp = getClientIp(httpRequest);
        if (!rateLimiterService.tryAcquire(action + ":" + clientIp)) {
            throw new TooManyRequestsException(ErrorMessages.TOO_MANY_REQUESTS);
        }
    }

    private String getClientIp(HttpServletRequest httpRequest) {
        String forwardedFor = httpRequest.getHeader("X-Forwarded-For");
        if (StringUtils.hasText(forwardedFor)) {
            return forwardedFor.split(",")[0].trim();
        }
        return httpRequest.getRemoteAddr();
    }

}
