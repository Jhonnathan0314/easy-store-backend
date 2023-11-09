package com.sophie.store.backend.context.purchase_has_product.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sophie.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductAddDTO;
import com.sophie.store.backend.context.purchase_has_product.application.usecase.*;
import com.sophie.store.backend.context.purchase_has_product.data.PurchaseHasProductData;
import com.sophie.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.sophie.store.backend.context.purchase_has_product.infrastructure.mappers.PurchaseHasProductAddMapper;
import com.sophie.store.backend.general.GeneralData;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import com.sophie.store.backend.utils.exceptions.NonExistenceException;
import com.sophie.store.backend.utils.messages.ApiResponse;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PurchaseHasProductControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PurchaseHasProductController purchaseHasProductController;

    @Mock
    private FindByIdPurchaseHasProductUseCase findByIdPurchaseHasProductUseCase;

    @Mock
    private FindAllByProductIdPurchaseHasProductUseCase findAllByProductIdPurchaseHasProductUseCase;

    @Mock
    private FindAllByPurchaseIdPurchaseHasProductUseCase findAllByPurchaseIdPurchaseHasProductUseCase;

    @Mock
    private AddPurchaseHasProductUseCase addPurchaseHasProductUseCase;

    @Mock
    private RemoveByIdPurchaseHasProductUseCase removeByIdPurchaseHasProductUseCase;

    @Mock
    private PurchaseHasProductAddMapper purchaseHasProductAddMapper;

    private ErrorMessages errorMessages;
    private PurchaseHasProductData purchaseHasProductData;
    private GeneralData generalData;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(purchaseHasProductController).build();
        purchaseHasProductData = new PurchaseHasProductData();
        generalData = new GeneralData();
        errorMessages = new ErrorMessages();
        purchaseHasProductAddMapper = new PurchaseHasProductAddMapper();
    }

    @Test
    @Order(0)
    void findByIdSuccess() throws Exception {
        when(findByIdPurchaseHasProductUseCase.findById(any(Long.class))).thenReturn(purchaseHasProductData.getPurchaseHasProductResponseOne());

        mockMvc.perform(get("/api/v1/purchase-has-product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").value(purchaseHasProductData.getPurchaseHasProductResponseOne().getId()))
                .andExpect(jsonPath("$.data.product.id").value(purchaseHasProductData.getPurchaseHasProductResponseOne().getProduct().getId()))
                .andExpect(jsonPath("$.data.purchase.id").value(purchaseHasProductData.getPurchaseHasProductResponseOne().getPurchase().getId()));

        verify(findByIdPurchaseHasProductUseCase).findById(any(Long.class));
    }

    @Test
    @Order(1)
    void findByIdFailedNoResultsException() throws Exception {
        ApiResponse<PurchaseHasProduct> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findByIdPurchaseHasProductUseCase.findById(any(Long.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/purchase-has-product/12345")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findByIdPurchaseHasProductUseCase).findById(any(Long.class));
    }

    @Test
    @Order(2)
    void findAllByProductIdSuccess() throws Exception {
        when(findAllByProductIdPurchaseHasProductUseCase.findAllByProductId(any(Long.class))).thenReturn(purchaseHasProductData.getPurchaseHasProductsList());

        mockMvc.perform(get("/api/v1/purchase-has-product/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());

        verify(findAllByProductIdPurchaseHasProductUseCase).findAllByProductId(any(Long.class));
    }

    @Test
    @Order(3)
    void findAllByProductIdFailedNoResultsException() throws Exception {
        ApiResponse<List<PurchaseHasProduct>> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findAllByProductIdPurchaseHasProductUseCase.findAllByProductId(any(Long.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/purchase-has-product/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findAllByProductIdPurchaseHasProductUseCase).findAllByProductId(any(Long.class));
    }

    @Test
    @Order(4)
    void findAllByPurchaseIdSuccess() throws Exception {
        when(findAllByPurchaseIdPurchaseHasProductUseCase.findAllByPurchaseId(any(Long.class))).thenReturn(purchaseHasProductData.getPurchaseHasProductsList());

        mockMvc.perform(get("/api/v1/purchase-has-product/purchase/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());

        verify(findAllByPurchaseIdPurchaseHasProductUseCase).findAllByPurchaseId(any(Long.class));
    }

    @Test
    @Order(5)
    void findAllByPurchaseIdFailedNoResultsException() throws Exception {
        ApiResponse<List<PurchaseHasProduct>> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findAllByPurchaseIdPurchaseHasProductUseCase.findAllByPurchaseId(any(Long.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/purchase-has-product/purchase/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findAllByPurchaseIdPurchaseHasProductUseCase).findAllByPurchaseId(any(Long.class));
    }

    @Test
    @Order(14)
    void addSuccess() throws Exception {
        when(addPurchaseHasProductUseCase.add(any(PurchaseHasProduct.class), any(Long.class), any(Long.class))).thenReturn(purchaseHasProductData.getPurchaseHasProductResponseOne());

        PurchaseHasProductAddDTO body = purchaseHasProductAddMapper.modelToDto(purchaseHasProductData.getPurchaseHasProductAddValid());

        mockMvc.perform(post("/api/v1/purchase-has-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Purchase-Id", 1)
                        .header("Product-Id", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").value(purchaseHasProductData.getPurchaseHasProductResponseOne().getId()))
                .andExpect(jsonPath("$.data.product.id").value(purchaseHasProductData.getPurchaseHasProductResponseOne().getProduct().getId()))
                .andExpect(jsonPath("$.data.purchase.id").value(purchaseHasProductData.getPurchaseHasProductResponseOne().getPurchase().getId()));

        verify(addPurchaseHasProductUseCase).add(any(PurchaseHasProduct.class), any(Long.class), any(Long.class));
    }

    @Test
    @Order(7)
    void addFailedInvalidBodyException() throws Exception {
        ApiResponse<PurchaseHasProduct> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorInvalidBody());

        when(addPurchaseHasProductUseCase.add(any(PurchaseHasProduct.class), any(Long.class), any(Long.class)))
                .thenThrow(new InvalidBodyException(errorMessages.INVALID_BODY));

        PurchaseHasProductAddDTO body = purchaseHasProductAddMapper.modelToDto(purchaseHasProductData.getPurchaseHasProductAddValid());

        mockMvc.perform(post("/api/v1/purchase-has-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Create-By", 1)
                        .header("Purchase-Id", 1)
                        .header("Product-Id", 1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(addPurchaseHasProductUseCase).add(any(PurchaseHasProduct.class), any(Long.class), any(Long.class));
    }

    @Test
    @Order(16)
    void removeByIdSuccess() throws Exception {
        mockMvc.perform(delete("/api/v1/purchase-has-product/1"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").doesNotExist());

        verify(removeByIdPurchaseHasProductUseCase).removeById(any(Long.class));
    }

    @Test
    @Order(17)
    void removeByIdFailedNonExistenceException() throws Exception {
        ApiResponse<PurchaseHasProduct> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNonExistence());

        doThrow(new NonExistenceException(errorMessages.NON_EXISTENT_DATA))
                .when(removeByIdPurchaseHasProductUseCase).removeById(any(Long.class));

        mockMvc.perform(delete("/api/v1/purchase-has-product/12345"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(removeByIdPurchaseHasProductUseCase).removeById(any(Long.class));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}