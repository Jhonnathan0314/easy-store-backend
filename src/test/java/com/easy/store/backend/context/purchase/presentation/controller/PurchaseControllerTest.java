package com.easy.store.backend.context.purchase.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.easy.store.backend.context.purchase.application.dto.PurchaseGenerateDTO;
import com.easy.store.backend.context.purchase.application.usecase.*;
import com.easy.store.backend.context.purchase.data.PurchaseData;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.infrastructure.mappers.PurchaseGenerateMapper;
import com.easy.store.backend.general.GeneralData;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import com.easy.store.backend.utils.messages.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PurchaseControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PurchaseController purchaseController;

    @Mock
    private FindAllPurchaseUseCase findAllPurchaseUseCase;

    @Mock
    private FindByIdPurchaseUseCase findByIdPurchaseUseCase;

    @Mock
    private FindByUserIdPurchaseUseCase findByUserIdPurchaseUseCase;

    @Mock
    private FindByPaymentTypeIdPurchaseUseCase findByPaymentTypeIdPurchaseUseCase;

    @Mock
    private FindByDatePurchaseUseCase findByDatePurchaseUseCase;

    @Mock
    private FindByDateBetweenPurchaseUseCase findByDateBetweenPurchaseUseCase;

    @Mock
    private FindByTotalBetweenPurchaseUseCase findByTotalBetweenPurchaseUseCase;

    @Mock
    private GeneratePurchaseUseCase generatePurchaseUseCase;

    @Mock
    private DeleteByIdPurchaseUseCase deleteByIdPurchaseUseCase;

    @Mock
    private PurchaseGenerateMapper purchaseGenerateMapper;

    private ErrorMessages errorMessages;
    private PurchaseData purchaseData;
    private GeneralData generalData;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(purchaseController).build();
        purchaseData = new PurchaseData();
        generalData = new GeneralData();
        errorMessages = new ErrorMessages();
        purchaseGenerateMapper = new PurchaseGenerateMapper();
    }

    @Test
    @Order(0)
    void findAllSuccess() throws Exception {
        when(findAllPurchaseUseCase.findAll()).thenReturn(purchaseData.getPurchasesList());

        mockMvc.perform(get("/api/v1/purchase")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());

        verify(findAllPurchaseUseCase).findAll();
    }

    @Test
    @Order(1)
    void findAllFailedNoResultsException() throws Exception {
        ApiResponse<List<Purchase>> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findAllPurchaseUseCase.findAll())
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/purchase")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findAllPurchaseUseCase).findAll();
    }

    @Test
    @Order(2)
    void findByIdSuccess() throws Exception {
        when(findByIdPurchaseUseCase.findById(any(Long.class))).thenReturn(purchaseData.getPurchaseResponseOne());

        mockMvc.perform(get("/api/v1/purchase/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").value(purchaseData.getPurchaseResponseOne().getId()))
                .andExpect(jsonPath("$.data.user.id").value(purchaseData.getPurchaseResponseOne().getUser().getId()))
                .andExpect(jsonPath("$.data.paymentType.id").value(purchaseData.getPurchaseResponseOne().getPaymentType().getId()));

        verify(findByIdPurchaseUseCase).findById(any(Long.class));
    }

    @Test
    @Order(3)
    void findByIdFailedNoResultsException() throws Exception {
        ApiResponse<Purchase> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findByIdPurchaseUseCase.findById(any(Long.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/purchase/12345")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findByIdPurchaseUseCase).findById(any(Long.class));
    }

    @Test
    @Order(4)
    void findByUserIdSuccess() throws Exception {
        when(findByUserIdPurchaseUseCase.findByUserId(any(Long.class))).thenReturn(purchaseData.getPurchasesList());

        mockMvc.perform(get("/api/v1/purchase/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());

        verify(findByUserIdPurchaseUseCase).findByUserId(any(Long.class));
    }

    @Test
    @Order(5)
    void findByUserIdFailedNoResultsException() throws Exception {
        ApiResponse<List<Purchase>> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findByUserIdPurchaseUseCase.findByUserId(any(Long.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/purchase/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findByUserIdPurchaseUseCase).findByUserId(any(Long.class));
    }

    @Test
    @Order(6)
    void findByPaymentTypeIdSuccess() throws Exception {
        when(findByPaymentTypeIdPurchaseUseCase.findByPaymentTypeId(any(Long.class))).thenReturn(purchaseData.getPurchasesList());

        mockMvc.perform(get("/api/v1/purchase/payment-type/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());

        verify(findByPaymentTypeIdPurchaseUseCase).findByPaymentTypeId(any(Long.class));
    }

    @Test
    @Order(7)
    void findByPaymentTypeIdFailedNoResultsException() throws Exception {
        ApiResponse<List<Purchase>> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findByPaymentTypeIdPurchaseUseCase.findByPaymentTypeId(any(Long.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/purchase/payment-type/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findByPaymentTypeIdPurchaseUseCase).findByPaymentTypeId(any(Long.class));
    }

    @Test
    @Order(8)
    void findByDateSuccess() throws Exception {
        when(findByDatePurchaseUseCase.findByDate(any(Timestamp.class))).thenReturn(purchaseData.getPurchasesList());

        mockMvc.perform(get("/api/v1/purchase/date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Creation-Date", "2023-11-09"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());

        verify(findByDatePurchaseUseCase).findByDate(any(Timestamp.class));
    }

    @Test
    @Order(9)
    void findByDateFailedNoResultsException() throws Exception {
        ApiResponse<List<Purchase>> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findByDatePurchaseUseCase.findByDate(any(Timestamp.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/purchase/date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Creation-Date", "2023-11-09"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findByDatePurchaseUseCase).findByDate(any(Timestamp.class));
    }

    @Test
    @Order(10)
    void findByDateBetweenSuccess() throws Exception {
        when(findByDateBetweenPurchaseUseCase.findByDateBetween(any(Timestamp.class), any(Timestamp.class))).thenReturn(purchaseData.getPurchasesList());

        mockMvc.perform(get("/api/v1/purchase/range/dates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("From-Date", "2023-11-05")
                        .header("To-Date", "2023-11-09"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());

        verify(findByDateBetweenPurchaseUseCase).findByDateBetween(any(Timestamp.class), any(Timestamp.class));
    }

    @Test
    @Order(11)
    void findByDateBetweenFailedNoResultsException() throws Exception {
        ApiResponse<List<Purchase>> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findByDateBetweenPurchaseUseCase.findByDateBetween(any(Timestamp.class), any(Timestamp.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/purchase/range/dates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("From-Date", "2023-11-05")
                        .header("To-Date", "2023-11-09"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findByDateBetweenPurchaseUseCase).findByDateBetween(any(Timestamp.class), any(Timestamp.class));
    }

    @Test
    @Order(12)
    void findByTotalBetweenSuccess() throws Exception {
        when(findByTotalBetweenPurchaseUseCase.findByTotalBetween(any(BigDecimal.class), any(BigDecimal.class))).thenReturn(purchaseData.getPurchasesList());

        mockMvc.perform(get("/api/v1/purchase/range/total")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("From-Total", "1000")
                        .header("To-Total", "5000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());

        verify(findByTotalBetweenPurchaseUseCase).findByTotalBetween(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    @Order(13)
    void findByTotalBetweenFailedNoResultsException() throws Exception {
        ApiResponse<List<Purchase>> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findByTotalBetweenPurchaseUseCase.findByTotalBetween(any(BigDecimal.class), any(BigDecimal.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/purchase/range/total")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("From-Total", "1000")
                        .header("To-Total", "5000"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findByTotalBetweenPurchaseUseCase).findByTotalBetween(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    @Order(14)
    void generateSuccess() throws Exception {
        when(generatePurchaseUseCase.generate(any(Purchase.class), any(Long.class), any(Long.class))).thenReturn(purchaseData.getPurchaseResponseOne());

        PurchaseGenerateDTO body = purchaseGenerateMapper.modelToDto(purchaseData.getPurchaseGenerateValid());

        mockMvc.perform(post("/api/v1/purchase/user/1/payment-type/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Create-By", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").value(purchaseData.getPurchaseResponseOne().getId()))
                .andExpect(jsonPath("$.data.user.id").value(purchaseData.getPurchaseResponseOne().getUser().getId()))
                .andExpect(jsonPath("$.data.paymentType.id").value(purchaseData.getPurchaseResponseOne().getPaymentType().getId()));

        verify(generatePurchaseUseCase).generate(any(Purchase.class), any(Long.class), any(Long.class));
    }

    @Test
    @Order(15)
    void generateFailedInvalidBodyException() throws Exception {
        ApiResponse<Purchase> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorInvalidBody());

        when(generatePurchaseUseCase.generate(any(Purchase.class), any(Long.class), any(Long.class)))
                .thenThrow(new InvalidBodyException(errorMessages.INVALID_BODY));

        PurchaseGenerateDTO body = purchaseGenerateMapper.modelToDto(purchaseData.getPurchaseGenerateValid());

        mockMvc.perform(post("/api/v1/purchase/user/1/payment-type/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Create-By", 1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(generatePurchaseUseCase).generate(any(Purchase.class), any(Long.class), any(Long.class));
    }

    @Test
    @Order(16)
    void deleteByIdSuccess() throws Exception {
        mockMvc.perform(delete("/api/v1/purchase/1"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").doesNotExist());

        verify(deleteByIdPurchaseUseCase).deleteById(any(Long.class));
    }

    @Test
    @Order(17)
    void deleteByIdFailedNonExistenceException() throws Exception {
        ApiResponse<Purchase> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNonExistence());

        doThrow(new NonExistenceException(errorMessages.NON_EXISTENT_DATA))
                .when(deleteByIdPurchaseUseCase).deleteById(any(Long.class));

        mockMvc.perform(delete("/api/v1/purchase/12345"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(deleteByIdPurchaseUseCase).deleteById(any(Long.class));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}