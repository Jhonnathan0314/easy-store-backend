package com.sophie.store.backend.context.user.presentation.controller;

import com.sophie.store.backend.context.roles.application.dto.RoleDTO;
import com.sophie.store.backend.context.user.application.dto.UserDTO;
import com.sophie.store.backend.context.user.application.usecase.*;
import com.sophie.store.backend.context.user.domain.model.User;
import com.sophie.store.backend.context.user.infrastructure.mappers.UserMapper;
import com.sophie.store.backend.utils.exceptions.*;
import com.sophie.store.backend.utils.http.HttpUtils;
import com.sophie.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final FindAllUserUseCase findAllUserUseCase;
    private final FindByIdUserUseCase findByIdUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteByIdUserUseCase deleteByIdUserUseCase;
    private final ChangeStateByIdUserUseCase changeStateByIdUserUseCase;

    private final UserMapper userMapper = new UserMapper();
    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> findAll() {
        ApiResponse<List<UserDTO>> response = new ApiResponse<>();
        try {
            List<UserDTO> users = userMapper.modelsToDtos(findAllUserUseCase.findAll());
            response.setData(users);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> findById(@PathVariable Long id) {
        ApiResponse<UserDTO> response = new ApiResponse<>();
        try {
            User user = findByIdUserUseCase.findById(id);
            response.setData(userMapper.modelToDto(user));
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> create(@RequestBody UserDTO user) {
        ApiResponse<UserDTO> response = new ApiResponse<>();
        try {
            user.setRole(defaultRole());
            response.setData(userMapper.modelToDto(createUserUseCase.create(userMapper.dtoToModel(user))));
            return ResponseEntity.ok(response);
        } catch (DuplicatedException | InvalidBodyException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<UserDTO>> update(@RequestBody UserDTO user) {
        ApiResponse<UserDTO> response = new ApiResponse<>();
        try {
            response.setData(userMapper.modelToDto(updateUserUseCase.update(userMapper.dtoToModel(user))));
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
        } catch (NonExisteceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("/change-state/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> changeStateById(@PathVariable Long id) {
        ApiResponse<UserDTO> response = new ApiResponse<>();
        try {
            User user = changeStateByIdUserUseCase.changeStateById(id);
            response.setData(userMapper.modelToDto(user));
            return ResponseEntity.ok(response);
        } catch (NonExisteceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    private RoleDTO defaultRole() {
        return RoleDTO.builder()
                .id(1L)
                .name("client")
                .build();
    }
}
