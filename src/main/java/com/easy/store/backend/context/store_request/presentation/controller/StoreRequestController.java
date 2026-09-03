package com.easy.store.backend.context.store_request.presentation.controller;

import com.easy.store.backend.context.store_request.application.dto.StoreRequestCreateDTO;
import com.easy.store.backend.context.store_request.application.dto.StoreRequestResponseDTO;
import com.easy.store.backend.context.store_request.application.service.StoreRequestAuthorizationService;
import com.easy.store.backend.context.store_request.application.usecase.*;
import com.easy.store.backend.context.store_request.domain.model.StoreRequest;
import com.easy.store.backend.context.store_request.infrastructure.mappers.StoreRequestResponseMapper;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.utils.exceptions.*;
import com.easy.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/store-request")
@RequiredArgsConstructor
public class StoreRequestController {

    private final CreateStoreRequestUseCase createStoreRequestUseCase;
    private final FindPendingStoreRequestsUseCase findPendingStoreRequestsUseCase;
    private final FindByUserIdStoreRequestUseCase findByUserIdStoreRequestUseCase;
    private final ApproveStoreRequestUseCase approveStoreRequestUseCase;
    private final RejectStoreRequestUseCase rejectStoreRequestUseCase;
    private final StoreRequestAuthorizationService storeRequestAuthorizationService;

    private final StoreRequestResponseMapper storeRequestResponseMapper = new StoreRequestResponseMapper();

    @PostMapping
    public ResponseEntity<ApiResponse<StoreRequestResponseDTO>> create(@RequestBody StoreRequestCreateDTO body,
                                                        @RequestHeader("Create-By") Long createBy)
            throws InvalidBodyException, NoResultsException, InvalidActionException {
        ApiResponse<StoreRequestResponseDTO> response = new ApiResponse<>();
        StoreRequest storeRequest = StoreRequest.builder()
                .user(User.builder().id(createBy).build())
                .storeName(body.getStoreName())
                .storeDescription(body.getStoreDescription())
                .build();
        response.setData(storeRequestResponseMapper.modelToDto(createStoreRequestUseCase.create(storeRequest)));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<StoreRequestResponseDTO>>> findPending() throws NoResultsException {
        ApiResponse<List<StoreRequestResponseDTO>> response = new ApiResponse<>();
        response.setData(storeRequestResponseMapper.modelsToDtos(findPendingStoreRequestsUseCase.findPending()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<StoreRequestResponseDTO>>> findByUserId(@PathVariable Long userId) throws NoResultsException, ForbiddenActionException {
        storeRequestAuthorizationService.authorizeFindByUserId(userId);
        ApiResponse<List<StoreRequestResponseDTO>> response = new ApiResponse<>();
        response.setData(storeRequestResponseMapper.modelsToDtos(findByUserIdStoreRequestUseCase.findByUserId(userId)));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<StoreRequestResponseDTO>> approve(@PathVariable Long id,
                                                         @RequestHeader("Update-By") Long updateBy)
            throws NoResultsException, InvalidActionException {
        ApiResponse<StoreRequestResponseDTO> response = new ApiResponse<>();
        response.setData(storeRequestResponseMapper.modelToDto(approveStoreRequestUseCase.approve(id, updateBy)));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<StoreRequestResponseDTO>> reject(@PathVariable Long id,
                                                        @RequestHeader("Update-By") Long updateBy)
            throws NoResultsException, InvalidActionException {
        ApiResponse<StoreRequestResponseDTO> response = new ApiResponse<>();
        response.setData(storeRequestResponseMapper.modelToDto(rejectStoreRequestUseCase.reject(id, updateBy)));
        return ResponseEntity.ok(response);
    }

}
