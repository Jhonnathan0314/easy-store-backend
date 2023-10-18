package com.sophie.store.backend.context.roles.presentation.controller;

import com.sophie.store.backend.context.roles.application.dto.RoleDTO;
import com.sophie.store.backend.context.roles.application.usecase.*;
import com.sophie.store.backend.context.roles.domain.model.Role;
import com.sophie.store.backend.context.roles.infrastructure.mappers.RoleMapper;
import com.sophie.store.backend.utils.http.HttpUtils;
import com.sophie.store.backend.utils.messages.ApiResponse;
import com.sophie.store.backend.utils.messages.ErrorMessage;
import com.sophie.store.backend.utils.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final FindAllRoleUseCase findAllRoleUseCase;
    private final FindByIdRoleUseCase findByIdRoleUseCase;
    private final CreateRoleUseCase createRoleUseCase;
    private final UpdateRoleUseCase updateRoleUseCase;
    private final DeleteByIdRoleUseCase deleteByIdRoleUseCase;
    private final ChangeStateByIdRoleUseCase changeStateByIdRoleUseCase;

    private final RoleMapper roleMapper = new RoleMapper();
    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleDTO>>> findAll() {
        ApiResponse<List<RoleDTO>> response = new ApiResponse<>();
        try {
            List<RoleDTO> roles = roleMapper.modelsToDtos(findAllRoleUseCase.findAll());
            response.setData(roles);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleDTO>> findById(@PathVariable Long id) {
        ApiResponse<RoleDTO> response = new ApiResponse<>();
        try {
            Role role = findByIdRoleUseCase.findById(id);
            response.setData(roleMapper.modelToDto(role));
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RoleDTO>> create(@RequestBody RoleDTO role) {
        ApiResponse<RoleDTO> response = new ApiResponse<>();
        try {
            response.setData(roleMapper.modelToDto(createRoleUseCase.create(roleMapper.dtoToModel(role))));
            return ResponseEntity.ok(response);
        } catch (DuplicatedException | InvalidBodyException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<RoleDTO>> update(@RequestBody RoleDTO role) {
        ApiResponse<RoleDTO> response = new ApiResponse<>();
        try {
            response.setData(roleMapper.modelToDto(updateRoleUseCase.update(roleMapper.dtoToModel(role))));
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
            deleteByIdRoleUseCase.deleteById(id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (NonExisteceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("/change-state/{id}")
    public ResponseEntity<ApiResponse<RoleDTO>> changeStateById(@PathVariable Long id) {
        ApiResponse<RoleDTO> response = new ApiResponse<>();
        try {
            Role role = changeStateByIdRoleUseCase.changeStateById(id);
            response.setData(roleMapper.modelToDto(role));
            return ResponseEntity.ok(response);
        } catch (NonExisteceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}
