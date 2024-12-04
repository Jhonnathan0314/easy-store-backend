package com.easy.store.backend.context.payment_type.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.easy.store.backend.context.payment_type.data.PaymentTypeData;
import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeCreateDTO;
import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeUpdateDTO;
import com.easy.store.backend.context.payment_type.application.usecase.*;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeCreateMapper;
import com.easy.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeUpdateMapper;
import com.easy.store.backend.general.GeneralData;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.*;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PaymentTypeControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PaymentTypeController paymentTypeController;

    @Mock
    private FindAllPaymentTypeUseCase findAllPaymentTypeUseCase;

    @Mock
    private FindByIdPaymentTypeUseCase findByIdPaymentTypeUseCase;

    @Mock
    private CreatePaymentTypeUseCase createPaymentTypeUseCase;

    @Mock
    private UpdatePaymentTypeUseCase updatePaymentTypeUseCase;

    @Mock
    private DeleteByIdPaymentTypeUseCase deleteByIdPaymentTypeUseCase;

    @Mock
    private ChangeStateByIdPaymentTypeUseCase changeStateByIdPaymentTypeUseCase;

    @Mock
    private PaymentTypeCreateMapper paymentTypeCreateMapper;

    @Mock
    private PaymentTypeUpdateMapper paymentTypeUpdateMapper;

    private ErrorMessages errorMessages;
    private PaymentTypeData paymentTypeData;
    private GeneralData generalData;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentTypeController).build();
        paymentTypeData = new PaymentTypeData();
        generalData = new GeneralData();
        errorMessages = new ErrorMessages();
        paymentTypeCreateMapper = new PaymentTypeCreateMapper();
        paymentTypeUpdateMapper = new PaymentTypeUpdateMapper();
    }

    @Test
    @Order(0)
    void findAllSuccess() throws Exception {
        when(findAllPaymentTypeUseCase.findAll()).thenReturn(paymentTypeData.getPaymentTypesList());

        mockMvc.perform(get("/api/v1/payment-type")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());

        verify(findAllPaymentTypeUseCase).findAll();
    }

    @Test
    @Order(1)
    void findAllFailedNoResultsException() throws Exception {
        ApiResponse<List<PaymentType>> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findAllPaymentTypeUseCase.findAll())
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/payment-type")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findAllPaymentTypeUseCase).findAll();
    }

    @Test
    @Order(2)
    void findByIdSuccess() throws Exception {
        when(findByIdPaymentTypeUseCase.findById(any(Long.class))).thenReturn(paymentTypeData.getPaymentTypeResponseOne());

        mockMvc.perform(get("/api/v1/payment-type/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").value(paymentTypeData.getPaymentTypeResponseOne()));

        verify(findByIdPaymentTypeUseCase).findById(any(Long.class));
    }

    @Test
    @Order(3)
    void findByIdFailedNoResultsException() throws Exception {
        ApiResponse<PaymentType> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findByIdPaymentTypeUseCase.findById(any(Long.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/payment-type/12345")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findByIdPaymentTypeUseCase).findById(any(Long.class));
    }

    @Test
    @Order(4)
    void createSuccess() throws Exception {
        when(createPaymentTypeUseCase.create(any(PaymentType.class))).thenReturn(paymentTypeData.getPaymentTypeResponseOne());

        PaymentTypeCreateDTO body = paymentTypeCreateMapper.modelToDto(paymentTypeData.getPaymentTypeCreateValid());

        mockMvc.perform(post("/api/v1/payment-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Create-By", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").value(paymentTypeData.getPaymentTypeResponseOne()));

        verify(createPaymentTypeUseCase).create(any(PaymentType.class));
    }

    @Test
    @Order(5)
    void createFailedDuplicatedException() throws Exception {
        ApiResponse<PaymentType> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorDuplicated());

        when(createPaymentTypeUseCase.create(any(PaymentType.class)))
                .thenThrow(new DuplicatedException(errorMessages.DUPLICATED));

        PaymentTypeCreateDTO body = paymentTypeCreateMapper.modelToDto(paymentTypeData.getPaymentTypeCreateValid());

        mockMvc.perform(post("/api/v1/payment-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Create-By", 1))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(createPaymentTypeUseCase).create(any(PaymentType.class));
    }

    @Test
    @Order(6)
    void createFailedInvalidBodyException() throws Exception {
        ApiResponse<PaymentType> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorInvalidBody());

        when(createPaymentTypeUseCase.create(any(PaymentType.class)))
                .thenThrow(new InvalidBodyException(errorMessages.INVALID_BODY));

        PaymentTypeCreateDTO body = paymentTypeCreateMapper.modelToDto(paymentTypeData.getPaymentTypeCreateValid());

        mockMvc.perform(post("/api/v1/payment-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Create-By", 1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(createPaymentTypeUseCase).create(any(PaymentType.class));
    }

    @Test
    @Order(7)
    void updateSuccess() throws Exception {
        when(updatePaymentTypeUseCase.update(any(PaymentType.class))).thenReturn(paymentTypeData.getPaymentTypeResponseOne());

        PaymentTypeUpdateDTO body = paymentTypeUpdateMapper.modelToDto(paymentTypeData.getPaymentTypeToUpdate());

        mockMvc.perform(put("/api/v1/payment-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").value(paymentTypeData.getPaymentTypeResponseOne()));

        verify(updatePaymentTypeUseCase).update(any(PaymentType.class));
    }

    @Test
    @Order(8)
    void updateFailedNoIdReceivedException() throws Exception {
        ApiResponse<PaymentType> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoIdReceived());

        when(updatePaymentTypeUseCase.update(any(PaymentType.class)))
                .thenThrow(new NoIdReceivedException(errorMessages.NO_ID_RECEIVED));

        PaymentTypeUpdateDTO body = paymentTypeUpdateMapper.modelToDto(paymentTypeData.getPaymentTypeToUpdateNoId());

        mockMvc.perform(put("/api/v1/payment-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updatePaymentTypeUseCase).update(any(PaymentType.class));
    }

    @Test
    @Order(9)
    void updateFailedInvalidBodyException() throws Exception {
        ApiResponse<PaymentType> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorInvalidBody());

        when(updatePaymentTypeUseCase.update(any(PaymentType.class)))
                .thenThrow(new InvalidBodyException(errorMessages.INVALID_BODY));

        PaymentTypeUpdateDTO body = paymentTypeUpdateMapper.modelToDto(paymentTypeData.getPaymentTypeToUpdateInvalid());

        mockMvc.perform(put("/api/v1/payment-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updatePaymentTypeUseCase).update(any(PaymentType.class));
    }

    @Test
    @Order(10)
    void updateFailedNoResultsException() throws Exception {
        ApiResponse<PaymentType> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(updatePaymentTypeUseCase.update(any(PaymentType.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        PaymentTypeUpdateDTO body = paymentTypeUpdateMapper.modelToDto(paymentTypeData.getPaymentTypeInactive());

        mockMvc.perform(put("/api/v1/payment-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updatePaymentTypeUseCase).update(any(PaymentType.class));
    }

    @Test
    @Order(11)
    void updateFailedNoChangesException() throws Exception {
        ApiResponse<PaymentType> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoChanges());

        when(updatePaymentTypeUseCase.update(any(PaymentType.class)))
                .thenThrow(new NoChangesException(errorMessages.NO_CHANGES));

        PaymentTypeUpdateDTO body = paymentTypeUpdateMapper.modelToDto(paymentTypeData.getPaymentTypeToUpdate());

        mockMvc.perform(put("/api/v1/payment-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updatePaymentTypeUseCase).update(any(PaymentType.class));
    }

    @Test
    @Order(12)
    void deleteByIdSuccess() throws Exception {
        mockMvc.perform(delete("/api/v1/payment-type/delete/1"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").doesNotExist());

        verify(deleteByIdPaymentTypeUseCase).deleteById(any(Long.class));
    }

    @Test
    @Order(13)
    void deleteByIdFailedNonExistenceException() throws Exception {
        ApiResponse<PaymentType> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNonExistence());

        doThrow(new NonExistenceException(errorMessages.NON_EXISTENT_DATA))
                .when(deleteByIdPaymentTypeUseCase).deleteById(any(Long.class));

        mockMvc.perform(delete("/api/v1/payment-type/delete/12345"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(deleteByIdPaymentTypeUseCase).deleteById(any(Long.class));
    }

    @Test
    @Order(14)
    void changeStateByIdSuccess() throws Exception {
        when(changeStateByIdPaymentTypeUseCase.changeStateById(any(Long.class)))
                .thenReturn(paymentTypeData.getPaymentTypeResponseOne());

        mockMvc.perform(delete("/api/v1/payment-type/change-state/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").value(paymentTypeData.getPaymentTypeResponseOne()));

        verify(changeStateByIdPaymentTypeUseCase).changeStateById(any(Long.class));
    }

    @Test
    @Order(15)
    void changeStateByIdFailedNonExistenceException() throws Exception {
        ApiResponse<PaymentType> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNonExistence());

        when(changeStateByIdPaymentTypeUseCase.changeStateById(any(Long.class)))
                .thenThrow(new NonExistenceException(errorMessages.NON_EXISTENT_DATA));

        mockMvc.perform(delete("/api/v1/payment-type/change-state/12345"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(changeStateByIdPaymentTypeUseCase).changeStateById(any(Long.class));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}