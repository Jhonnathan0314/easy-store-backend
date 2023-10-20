package com.sophie.store.backend.security.controllers;

import com.sophie.store.backend.context.user.application.dto.UserCreateDTO;
import com.sophie.store.backend.context.user.infrastructure.mappers.UserCreateMapper;
import com.sophie.store.backend.security.models.AuthResponse;
import com.sophie.store.backend.security.models.LoginRequest;
import com.sophie.store.backend.security.service.AuthorizationService;
import com.sophie.store.backend.utils.exceptions.DuplicatedException;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import com.sophie.store.backend.utils.http.HttpUtils;
import com.sophie.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authService;
    private final HttpUtils httpUtils = new HttpUtils();
    private final UserCreateMapper userCreateMapper = new UserCreateMapper();

    @PostMapping(value = "login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        ApiResponse<AuthResponse> response = new ApiResponse<>();
        try {
            response.setData(authService.login(request));
            return ResponseEntity.ok(response);
        } catch (NoResultsException | InvalidBodyException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping(value = "register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody UserCreateDTO request) {
        ApiResponse<AuthResponse> response = new ApiResponse<>();
        try {
            response.setData(authService.register(userCreateMapper.dtoToModel(request)));
            return ResponseEntity.ok(response);
        } catch (InvalidBodyException | DuplicatedException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}
