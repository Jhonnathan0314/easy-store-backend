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
    private final FindAllActivePaymentTypeUseCase findAllActivePaymentTypeUseCase;
    private final FindByIdPaymentTypeUseCase findByIdPaymentTypeUseCase;
    private final CreatePaymentTypeUseCase createPaymentTypeUseCase;
    private final UpdatePaymentTypeUseCase updatePaymentTypeUseCase;
    private final DeleteByIdPaymentTypeUseCase deleteByIdPaymentTypeUseCase;
    private final ChangeStateByIdPaymentTypeUseCase changeStateByIdPaymentTypeUseCase;

    private final PaymentTypeCreateMapper paymentTypeCreateMapper = new PaymentTypeCreateMapper();
    private final PaymentTypeUpdateMapper paymentTypeUpdateMapper = new PaymentTypeUpdateMapper();
    private final PaymentTypeResponseMapper paymentTypeResponseMapper = new PaymentTypeResponseMapper();

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentTypeResponseDTO>>> findAll() throws NoResultsException {
        ApiResponse<List<PaymentTypeResponseDTO>> response = new ApiResponse<>();
        List<PaymentTypeResponseDTO> paymentTypes = paymentTypeResponseMapper.modelsToDtos(findAllPaymentTypeUseCase.findAll());
        response.setData(paymentTypes);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<PaymentTypeResponseDTO>>> findAllActive() throws NoResultsException {
        ApiResponse<List<PaymentTypeResponseDTO>> response = new ApiResponse<>();
        List<PaymentTypeResponseDTO> paymentTypes = paymentTypeResponseMapper.modelsToDtos(findAllActivePaymentTypeUseCase.findAllActive());
        response.setData(paymentTypes);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentTypeResponseDTO>> findById(@PathVariable Long id) throws NoResultsException {
        ApiResponse<PaymentTypeResponseDTO> response = new ApiResponse<>();
        PaymentType paymentType = findByIdPaymentTypeUseCase.findById(id);
        response.setData(paymentTypeResponseMapper.modelToDto(paymentType));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentTypeResponseDTO>> create(@RequestBody PaymentTypeCreateDTO paymentType, @RequestHeader("Create-By") Long createBy) throws NoIdReceivedException, InvalidBodyException, NonExistenceException {
        ApiResponse<PaymentTypeResponseDTO> response = new ApiResponse<>();
        paymentType.setCreateBy(createBy);
        response.setData(paymentTypeResponseMapper.modelToDto(createPaymentTypeUseCase.create(paymentTypeCreateMapper.dtoToModel(paymentType))));
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<PaymentTypeResponseDTO>> update(@RequestBody PaymentTypeUpdateDTO paymentType, @RequestHeader("Update-By") Long updateBy) throws NoResultsException, NoIdReceivedException, NoChangesException, InvalidBodyException {
        ApiResponse<PaymentTypeResponseDTO> response = new ApiResponse<>();
        paymentType.setUpdateBy(updateBy);
        response.setData(paymentTypeResponseMapper.modelToDto(updatePaymentTypeUseCase.update(paymentTypeUpdateMapper.dtoToModel(paymentType))));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable Long id) throws NonExistenceException {
        ApiResponse<Object> response = new ApiResponse<>();
        deleteByIdPaymentTypeUseCase.deleteById(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/change-state/{id}")
    public ResponseEntity<ApiResponse<PaymentTypeResponseDTO>> changeStateById(@PathVariable Long id, @RequestHeader("Update-By") Long updateBy) throws NonExistenceException {
        ApiResponse<PaymentTypeResponseDTO> response = new ApiResponse<>();
        PaymentType paymentType = changeStateByIdPaymentTypeUseCase.changeStateById(id, updateBy);
        response.setData(paymentTypeResponseMapper.modelToDto(paymentType));
        return ResponseEntity.ok(response);
    }

}
