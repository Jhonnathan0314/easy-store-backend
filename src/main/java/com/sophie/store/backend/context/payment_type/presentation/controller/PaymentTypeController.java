package com.sophie.store.backend.context.payment_type.presentation.controller;

import com.sophie.store.backend.context.payment_type.application.dto.PaymentTypeCreateDTO;
import com.sophie.store.backend.context.payment_type.application.dto.PaymentTypeResponseDTO;
import com.sophie.store.backend.context.payment_type.application.dto.PaymentTypeUpdateDTO;
import com.sophie.store.backend.context.payment_type.application.usecase.*;
import com.sophie.store.backend.context.payment_type.domain.model.PaymentType;
import com.sophie.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeCreateMapper;
import com.sophie.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeResponseMapper;
import com.sophie.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeUpdateMapper;
import com.sophie.store.backend.utils.exceptions.*;
import com.sophie.store.backend.utils.http.HttpUtils;
import com.sophie.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-type")
@RequiredArgsConstructor
public class PaymentTypeController {

    private final FindAllPaymentTypeUseCase findAllPaymentTypeUseCase;
    private final FindByIdPaymentTypeUseCase findByIdPaymentTypeUseCase;
    private final CreatePaymentTypeUseCase createPaymentTypeUseCase;
    private final UpdatePaymentTypeUseCase updatePaymentTypeUseCase;
    private final DeleteByIdPaymentTypeUseCase deleteByIdPaymentTypeUseCase;
    private final ChangeStateByIdPaymentTypeUseCase changeStateByIdPaymentTypeUseCase;

    private final PaymentTypeCreateMapper categoryCreateMapper = new PaymentTypeCreateMapper();
    private final PaymentTypeUpdateMapper categoryUpdateMapper = new PaymentTypeUpdateMapper();
    private final PaymentTypeResponseMapper categoryResponseMapper = new PaymentTypeResponseMapper();
    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentTypeResponseDTO>>> findAll() {
        ApiResponse<List<PaymentTypeResponseDTO>> response = new ApiResponse<>();
        try {
            List<PaymentTypeResponseDTO> paymentTypes = categoryResponseMapper.modelsToDtos(findAllPaymentTypeUseCase.findAll());
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
            response.setData(categoryResponseMapper.modelToDto(paymentType));
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
            response.setData(categoryResponseMapper.modelToDto(createPaymentTypeUseCase.create(categoryCreateMapper.dtoToModel(paymentType))));
            return ResponseEntity.ok(response);
        } catch (DuplicatedException | InvalidBodyException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<PaymentTypeResponseDTO>> update(@RequestBody PaymentTypeUpdateDTO paymentType, @RequestHeader("Update-By") Long updateBy) {
        ApiResponse<PaymentTypeResponseDTO> response = new ApiResponse<>();
        try {
            paymentType.setUpdateBy(updateBy);
            response.setData(categoryResponseMapper.modelToDto(updatePaymentTypeUseCase.update(categoryUpdateMapper.dtoToModel(paymentType))));
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
        } catch (NonExisteceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("/change-state/{id}")
    public ResponseEntity<ApiResponse<PaymentTypeUpdateDTO>> changeStateById(@PathVariable Long id) {
        ApiResponse<PaymentTypeUpdateDTO> response = new ApiResponse<>();
        try {
            PaymentType paymentType = changeStateByIdPaymentTypeUseCase.changeStateById(id);
            response.setData(categoryUpdateMapper.modelToDto(paymentType));
            return ResponseEntity.ok(response);
        } catch (NonExisteceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}
