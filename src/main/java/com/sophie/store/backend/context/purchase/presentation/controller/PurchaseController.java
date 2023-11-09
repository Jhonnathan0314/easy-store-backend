package com.sophie.store.backend.context.purchase.presentation.controller;

import com.sophie.store.backend.context.purchase.application.dto.PurchaseGenerateDTO;
import com.sophie.store.backend.context.purchase.application.dto.PurchaseResponseDTO;
import com.sophie.store.backend.context.purchase.application.usecase.*;
import com.sophie.store.backend.context.purchase.domain.model.Purchase;
import com.sophie.store.backend.context.purchase.infrastructure.mappers.PurchaseGenerateMapper;
import com.sophie.store.backend.context.purchase.infrastructure.mappers.PurchaseResponseMapper;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import com.sophie.store.backend.utils.exceptions.NonExistenceException;
import com.sophie.store.backend.utils.http.HttpUtils;
import com.sophie.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final FindAllPurchaseUseCase findAllPurchaseUseCase;
    private final FindByIdPurchaseUseCase findByIdPurchaseUseCase;
    private final FindByUserIdPurchaseUseCase findByUserIdPurchaseUseCase;
    private final FindByPaymentTypeIdPurchaseUseCase findByPaymentTypeIdPurchaseUseCase;
    private final FindByDatePurchaseUseCase findByDatePurchaseUseCase;
    private final FindByDateBetweenPurchaseUseCase findByDateBetweenPurchaseUseCase;
    private final FindByTotalBetweenPurchaseUseCase findByTotalBetweenPurchaseUseCase;
    private final GeneratePurchaseUseCase generatePurchaseUseCase;
    private final DeleteByIdPurchaseUseCase deleteByIdPurchaseUseCase;

    private final PurchaseGenerateMapper purchaseGenerateMapper = new PurchaseGenerateMapper();
    private final PurchaseResponseMapper purchaseResponseMapper = new PurchaseResponseMapper();

    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping
    public ResponseEntity<ApiResponse<List<PurchaseResponseDTO>>> findAll() {
        ApiResponse<List<PurchaseResponseDTO>> response = new ApiResponse<>();
        try {
            List<PurchaseResponseDTO> purchases = purchaseResponseMapper.modelsToDtos(findAllPurchaseUseCase.findAll());
            response.setData(purchases);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseResponseDTO>> findById(@PathVariable Long id) {
        ApiResponse<PurchaseResponseDTO> response = new ApiResponse<>();
        try {
            Purchase purchase = findByIdPurchaseUseCase.findById(id);
            response.setData(purchaseResponseMapper.modelToDto(purchase));
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<PurchaseResponseDTO>>> findByUserId(@PathVariable Long userId) {
        ApiResponse<List<PurchaseResponseDTO>> response = new ApiResponse<>();
        try {
            List<PurchaseResponseDTO> purchases = purchaseResponseMapper.modelsToDtos(findByUserIdPurchaseUseCase.findByUserId(userId));
            response.setData(purchases);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/payment-type/{paymentTypeId}")
    public ResponseEntity<ApiResponse<List<PurchaseResponseDTO>>> findByPaymentTypeId(@PathVariable Long paymentTypeId) {
        ApiResponse<List<PurchaseResponseDTO>> response = new ApiResponse<>();
        try {
            List<PurchaseResponseDTO> purchases = purchaseResponseMapper.modelsToDtos(findByPaymentTypeIdPurchaseUseCase.findByPaymentTypeId(paymentTypeId));
            response.setData(purchases);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/date")
    public ResponseEntity<ApiResponse<List<PurchaseResponseDTO>>> findByDate(@RequestHeader("Creation-Date") String dateStr) {
        ApiResponse<List<PurchaseResponseDTO>> response = new ApiResponse<>();
        try {
            LocalDate date = LocalDate.parse(dateStr);
            LocalDateTime dateTime = date.atStartOfDay();
            Timestamp findDate = Timestamp.valueOf(dateTime);
            List<PurchaseResponseDTO> purchases = purchaseResponseMapper.modelsToDtos(findByDatePurchaseUseCase.findByDate(findDate));
            response.setData(purchases);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/range/dates")
    public ResponseEntity<ApiResponse<List<PurchaseResponseDTO>>> findBetweenRangeDates(@RequestHeader("From-Date") String fromDateStr, @RequestHeader("To-Date") String toDateStr) {
        ApiResponse<List<PurchaseResponseDTO>> response = new ApiResponse<>();
        try {
            LocalDate dateOne = LocalDate.parse(fromDateStr);
            LocalDateTime dateTimeOne = dateOne.atStartOfDay();
            Timestamp fromDate = Timestamp.valueOf(dateTimeOne);

            LocalDate dateTwo = LocalDate.parse(toDateStr);
            LocalDateTime dateTimeTwo = dateTwo.atStartOfDay();
            Timestamp toDate = Timestamp.valueOf(dateTimeTwo);

            List<PurchaseResponseDTO> purchases = purchaseResponseMapper.modelsToDtos(findByDateBetweenPurchaseUseCase.findByDateBetween(fromDate, toDate));
            response.setData(purchases);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/range/total")
    public ResponseEntity<ApiResponse<List<PurchaseResponseDTO>>> findBetweenRangeDates(@RequestHeader("From-Total") BigDecimal fromTotal, @RequestHeader("To-Total") BigDecimal toTotal) {
        ApiResponse<List<PurchaseResponseDTO>> response = new ApiResponse<>();
        try {
            List<PurchaseResponseDTO> purchases = purchaseResponseMapper.modelsToDtos(findByTotalBetweenPurchaseUseCase.findByTotalBetween(fromTotal, toTotal));
            response.setData(purchases);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping("/user/{userId}/payment-type/{paymentTypeId}")
    public ResponseEntity<ApiResponse<PurchaseResponseDTO>> generate(@RequestBody PurchaseGenerateDTO purchase,
                                                                     @PathVariable Long userId,
                                                                     @PathVariable Long paymentTypeId,
                                                                     @RequestHeader("Create-By") Long createBy) {
        ApiResponse<PurchaseResponseDTO> response = new ApiResponse<>();
        try {
            purchase.setCreateBy(createBy);
            response.setData(purchaseResponseMapper.modelToDto(generatePurchaseUseCase.generate(purchaseGenerateMapper.dtoToModel(purchase), userId, paymentTypeId)));
            return ResponseEntity.ok(response);
        } catch (InvalidBodyException | NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable Long id) {
        ApiResponse<Object> response = new ApiResponse<>();
        try {
            deleteByIdPurchaseUseCase.deleteById(id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (NonExistenceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}
