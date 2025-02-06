package com.easy.store.backend.context.payment_type.presentation.controller;

import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeCreateDTO;
import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeResponseDTO;
import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeUpdateDTO;
import com.easy.store.backend.context.payment_type.application.usecase.*;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeCreateMapper;
import com.easy.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeResponseMapper;
import com.easy.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeUpdateMapper;
import com.easy.store.backend.utils.exceptions.*;
import com.easy.store.backend.utils.http.HttpUtils;
import com.easy.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-type")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PaymentTypeController {

    private final FindAllPaymentTypeUseCase findAllPaymentTypeUseCase;
    private final FindByIdPaymentTypeUseCase findByIdPaymentTypeUseCase;
    private final FindByAccountIdPaymentTypeUseCase findByAccountIdPaymentTypeUseCase;
    private final CreatePaymentTypeUseCase createPaymentTypeUseCase;
    private final UpdatePaymentTypeUseCase updatePaymentTypeUseCase;
    private final DeleteByIdPaymentTypeUseCase deleteByIdPaymentTypeUseCase;
    private final ChangeStateByIdPaymentTypeUseCase changeStateByIdPaymentTypeUseCase;

    private final PaymentTypeCreateMapper paymentTypeCreateMapper = new PaymentTypeCreateMapper();
    private final PaymentTypeUpdateMapper paymentTypeUpdateMapper = new PaymentTypeUpdateMapper();
    private final PaymentTypeResponseMapper paymentTypeResponseMapper = new PaymentTypeResponseMapper();
    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentTypeResponseDTO>>> findAll() {
        ApiResponse<List<PaymentTypeResponseDTO>> response = new ApiResponse<>();
        try {
            List<PaymentTypeResponseDTO> paymentTypes = paymentTypeResponseMapper.modelsToDtos(findAllPaymentTypeUseCase.findAll());
            response.setData(paymentTypes);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentTypeResponseDTO>> findById(@PathVariable Long id) {
        ApiResponse<PaymentTypeResponseDTO> response = new ApiResponse<>();
        try {
            PaymentType paymentType = findByIdPaymentTypeUseCase.findById(id);
            response.setData(paymentTypeResponseMapper.modelToDto(paymentType));
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<List<PaymentTypeResponseDTO>>> findByAccountId(@PathVariable Long accountId) {
        ApiResponse<List<PaymentTypeResponseDTO>> response = new ApiResponse<>();
        try {
            List<PaymentTypeResponseDTO> paymentTypes = paymentTypeResponseMapper.modelsToDtos(findByAccountIdPaymentTypeUseCase.findByAccountId(accountId));
            response.setData(paymentTypes);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentTypeResponseDTO>> create(@RequestBody PaymentTypeCreateDTO paymentType, @RequestHeader("Create-By") Long createBy) {
        ApiResponse<PaymentTypeResponseDTO> response = new ApiResponse<>();
        try {
            paymentType.setCreateBy(createBy);
            response.setData(paymentTypeResponseMapper.modelToDto(createPaymentTypeUseCase.create(paymentTypeCreateMapper.dtoToModel(paymentType))));
            return ResponseEntity.ok(response);
        } catch (DuplicatedException | InvalidBodyException | NoIdReceivedException | NonExistenceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<PaymentTypeResponseDTO>> update(@RequestBody PaymentTypeUpdateDTO paymentType, @RequestHeader("Update-By") Long updateBy) {
        ApiResponse<PaymentTypeResponseDTO> response = new ApiResponse<>();
        try {
            paymentType.setUpdateBy(updateBy);
            response.setData(paymentTypeResponseMapper.modelToDto(updatePaymentTypeUseCase.update(paymentTypeUpdateMapper.dtoToModel(paymentType))));
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
            deleteByIdPaymentTypeUseCase.deleteById(id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (NonExistenceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("/change-state/{id}")
    public ResponseEntity<ApiResponse<PaymentTypeUpdateDTO>> changeStateById(@PathVariable Long id) {
        ApiResponse<PaymentTypeUpdateDTO> response = new ApiResponse<>();
        try {
            PaymentType paymentType = changeStateByIdPaymentTypeUseCase.changeStateById(id);
            response.setData(paymentTypeUpdateMapper.modelToDto(paymentType));
            return ResponseEntity.ok(response);
        } catch (NonExistenceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}
