package com.easy.store.backend.security.controllers;

import com.easy.store.backend.context.user.application.dto.UserCreateDTO;
import com.easy.store.backend.context.user.application.dto.UserResponseDTO;
import com.easy.store.backend.context.user.infrastructure.mappers.UserCreateMapper;
import com.easy.store.backend.context.user.infrastructure.mappers.UserResponseMapper;
import com.easy.store.backend.security.models.AuthResponse;
import com.easy.store.backend.security.models.LoginRequest;
import com.easy.store.backend.security.models.ResetPasswordRequest;
import com.easy.store.backend.security.service.AuthorizationService;
import com.easy.store.backend.utils.exceptions.*;
import com.easy.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthorizationController {

    private final AuthorizationService authService;

    private final UserCreateMapper userCreateMapper = new UserCreateMapper();
    private final UserResponseMapper userResponseMapper = new UserResponseMapper();

    @PostMapping(value = "login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) throws NoResultsException, InvalidBodyException {
        ApiResponse<AuthResponse> response = new ApiResponse<>();
        response.setData(authService.login(request));
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody UserCreateDTO request) throws NoResultsException, NoIdReceivedException, InvalidBodyException, NoChangesException, DuplicatedException, NonExistenceException {
        ApiResponse<AuthResponse> response = new ApiResponse<>();
        response.setData(authService.register(userCreateMapper.dtoToModel(request)));
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "reset-password")
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(@RequestBody ResetPasswordRequest request) throws NoResultsException, NoIdReceivedException, InvalidBodyException, NoChangesException, NonExistenceException {
        ApiResponse<UserResponseDTO> response = new ApiResponse<>();
        response.setData(userResponseMapper.modelToDto(authService.resetPassword(request)));
        return ResponseEntity.ok(response);
    }

}
