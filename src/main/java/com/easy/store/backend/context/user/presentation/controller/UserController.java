package com.easy.store.backend.context.user.presentation.controller;

import com.easy.store.backend.context.user.application.dto.UserCreateDTO;
import com.easy.store.backend.context.user.application.dto.UserResponseDTO;
import com.easy.store.backend.context.user.application.dto.UserUpdateDTO;
import com.easy.store.backend.context.user.application.usecase.*;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.infrastructure.mappers.UserCreateMapper;
import com.easy.store.backend.context.user.infrastructure.mappers.UserResponseMapper;
import com.easy.store.backend.context.user.infrastructure.mappers.UserUpdateMapper;
import com.easy.store.backend.utils.exceptions.*;
import com.easy.store.backend.utils.http.HttpUtils;
import com.easy.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final FindAllUserUseCase findAllUserUseCase;
    private final FindByIdUserUseCase findByIdUserUseCase;
    private final FindByAccountIdUserUseCase findByAccountIdUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteByIdUserUseCase deleteByIdUserUseCase;
    private final ChangeStateByIdUserUseCase changeStateByIdUserUseCase;

    private final UserCreateMapper userCreateMapper = new UserCreateMapper();
    private final UserUpdateMapper userUpdateMapper = new UserUpdateMapper();
    private final UserResponseMapper userResponseMapper = new UserResponseMapper();
    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> findAll() {
        ApiResponse<List<UserResponseDTO>> response = new ApiResponse<>();
        try {
            List<UserResponseDTO> users = userResponseMapper.modelsToDtos(findAllUserUseCase.findAll());
            response.setData(users);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> findById(@PathVariable Long id) {
        ApiResponse<UserResponseDTO> response = new ApiResponse<>();
        try {
            User user = findByIdUserUseCase.findById(id);
            response.setData(userResponseMapper.modelToDto(user));
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> findByAccountId(@PathVariable Long accountId) {
        ApiResponse<List<UserResponseDTO>> response = new ApiResponse<>();
        try {
            List<UserResponseDTO> users = userResponseMapper.modelsToDtos(findByAccountIdUserUseCase.findByAccountId(accountId));
            response.setData(users);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> create(@RequestBody UserCreateDTO user) {
        ApiResponse<UserResponseDTO> response = new ApiResponse<>();
        try {
            response.setData(userResponseMapper.modelToDto(createUserUseCase.create(userCreateMapper.dtoToModel(user))));
            return ResponseEntity.ok(response);
        } catch (DuplicatedException | InvalidBodyException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> update(@RequestBody UserUpdateDTO user) {
        ApiResponse<UserResponseDTO> response = new ApiResponse<>();
        try {
            response.setData(userResponseMapper.modelToDto(updateUserUseCase.update(userUpdateMapper.dtoToModel(user))));
            return ResponseEntity.ok(response);
        } catch (NoIdReceivedException | InvalidBodyException | NoResultsException | NoChangesException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable Long id) {
        ApiResponse<Object> response = new ApiResponse<>();
        try {
            deleteByIdUserUseCase.deleteById(id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (NonExistenceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("/change-state/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> changeStateById(@PathVariable Long id) {
        ApiResponse<UserResponseDTO> response = new ApiResponse<>();
        try {
            User user = changeStateByIdUserUseCase.changeStateById(id);
            response.setData(userResponseMapper.modelToDto(user));
            return ResponseEntity.ok(response);
        } catch (NonExistenceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }
}
