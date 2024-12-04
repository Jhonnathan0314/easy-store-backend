package com.easy.store.backend.context.subcategory.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.easy.store.backend.context.subcategory.application.dto.SubcategoryCreateDTO;
import com.easy.store.backend.context.subcategory.application.dto.SubcategoryUpdateDTO;
import com.easy.store.backend.context.subcategory.application.usecase.*;
import com.easy.store.backend.context.subcategory.data.SubcategoryData;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.infrastructure.mappers.SubcategoryCreateMapper;
import com.easy.store.backend.context.subcategory.infrastructure.mappers.SubcategoryUpdateMapper;
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
class SubcategoryControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private SubcategoryController subcategoryController;

    @Mock
    private FindAllSubcategoryUseCase findAllSubcategoryUseCase;

    @Mock
    private FindByIdSubcategoryUseCase findByIdSubcategoryUseCase;

    @Mock
    private CreateSubcategoryUseCase createSubcategoryUseCase;

    @Mock
    private UpdateSubcategoryUseCase updateSubcategoryUseCase;

    @Mock
    private DeleteByIdSubcategoryUseCase deleteByIdSubcategoryUseCase;

    @Mock
    private ChangeStateByIdSubcategoryUseCase changeStateByIdSubcategoryUseCase;

    @Mock
    private SubcategoryCreateMapper subcategoryCreateMapper;

    @Mock
    private SubcategoryUpdateMapper subcategoryUpdateMapper;

    private ErrorMessages errorMessages;
    private SubcategoryData subcategoryData;
    private GeneralData generalData;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(subcategoryController).build();
        subcategoryData = new SubcategoryData();
        generalData = new GeneralData();
        errorMessages = new ErrorMessages();
        subcategoryCreateMapper = new SubcategoryCreateMapper();
        subcategoryUpdateMapper = new SubcategoryUpdateMapper();
    }

    @Test
    @Order(0)
    void findAllSuccess() throws Exception {
        when(findAllSubcategoryUseCase.findAll()).thenReturn(subcategoryData.getSubcategorysList());

        mockMvc.perform(get("/api/v1/subcategory")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());

        verify(findAllSubcategoryUseCase).findAll();
    }

    @Test
    @Order(1)
    void findAllFailedNoResultsException() throws Exception {
        ApiResponse<List<Subcategory>> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findAllSubcategoryUseCase.findAll())
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/subcategory")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findAllSubcategoryUseCase).findAll();
    }

    @Test
    @Order(2)
    void findByIdSuccess() throws Exception {
        when(findByIdSubcategoryUseCase.findById(any(Long.class))).thenReturn(subcategoryData.getSubcategoryResponseOne());

        System.out.println("productData.getProductResponseOne() " + subcategoryData.getSubcategoryResponseOne().toString());

        mockMvc.perform(get("/api/v1/subcategory/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andDo((res) -> System.out.println("res: " + res.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.data").value(subcategoryData.getSubcategoryResponseOne()));

        verify(findByIdSubcategoryUseCase).findById(any(Long.class));
    }

    @Test
    @Order(3)
    void findByIdFailedNoResultsException() throws Exception {
        ApiResponse<Subcategory> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findByIdSubcategoryUseCase.findById(any(Long.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/subcategory/12345")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findByIdSubcategoryUseCase).findById(any(Long.class));
    }

    @Test
    @Order(4)
    void createSuccess() throws Exception {
        when(createSubcategoryUseCase.create(any(Subcategory.class), any(Long.class))).thenReturn(subcategoryData.getSubcategoryResponseOne());

        SubcategoryCreateDTO body = subcategoryCreateMapper.modelToDto(subcategoryData.getSubcategoryCreateValid());

        mockMvc.perform(post("/api/v1/subcategory/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Create-By", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").value(subcategoryData.getSubcategoryResponseOne()));

        verify(createSubcategoryUseCase).create(any(Subcategory.class), any(Long.class));
    }

    @Test
    @Order(5)
    void createFailedDuplicatedException() throws Exception {
        ApiResponse<Subcategory> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorDuplicated());

        when(createSubcategoryUseCase.create(any(Subcategory.class), any(Long.class)))
                .thenThrow(new DuplicatedException(errorMessages.DUPLICATED));

        SubcategoryCreateDTO body = subcategoryCreateMapper.modelToDto(subcategoryData.getSubcategoryCreateValid());

        mockMvc.perform(post("/api/v1/subcategory/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Create-By", 1))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(createSubcategoryUseCase).create(any(Subcategory.class), any(Long.class));
    }

    @Test
    @Order(6)
    void createFailedInvalidBodyException() throws Exception {
        ApiResponse<Subcategory> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorInvalidBody());

        when(createSubcategoryUseCase.create(any(Subcategory.class), any(Long.class)))
                .thenThrow(new InvalidBodyException(errorMessages.INVALID_BODY));

        SubcategoryCreateDTO body = subcategoryCreateMapper.modelToDto(subcategoryData.getSubcategoryCreateValid());

        mockMvc.perform(post("/api/v1/subcategory/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Create-By", 1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(createSubcategoryUseCase).create(any(Subcategory.class), any(Long.class));
    }

    @Test
    @Order(7)
    void updateSuccess() throws Exception {
        when(updateSubcategoryUseCase.update(any(Subcategory.class), any(Long.class))).thenReturn(subcategoryData.getSubcategoryResponseOne());

        SubcategoryUpdateDTO body = subcategoryUpdateMapper.modelToDto(subcategoryData.getSubcategoryToUpdate());

        mockMvc.perform(put("/api/v1/subcategory/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").value(subcategoryData.getSubcategoryResponseOne()));

        verify(updateSubcategoryUseCase).update(any(Subcategory.class), any(Long.class));
    }

    @Test
    @Order(8)
    void updateFailedNoIdReceivedException() throws Exception {
        ApiResponse<Subcategory> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoIdReceived());

        when(updateSubcategoryUseCase.update(any(Subcategory.class), any(Long.class)))
                .thenThrow(new NoIdReceivedException(errorMessages.NO_ID_RECEIVED));

        SubcategoryUpdateDTO body = subcategoryUpdateMapper.modelToDto(subcategoryData.getSubcategoryToUpdateNoId());

        mockMvc.perform(put("/api/v1/subcategory/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateSubcategoryUseCase).update(any(Subcategory.class), any(Long.class));
    }

    @Test
    @Order(9)
    void updateFailedInvalidBodyException() throws Exception {
        ApiResponse<Subcategory> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorInvalidBody());

        when(updateSubcategoryUseCase.update(any(Subcategory.class), any(Long.class)))
                .thenThrow(new InvalidBodyException(errorMessages.INVALID_BODY));

        SubcategoryUpdateDTO body = subcategoryUpdateMapper.modelToDto(subcategoryData.getSubcategoryToUpdateInvalid());

        mockMvc.perform(put("/api/v1/subcategory/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateSubcategoryUseCase).update(any(Subcategory.class), any(Long.class));
    }

    @Test
    @Order(10)
    void updateFailedNoResultsException() throws Exception {
        ApiResponse<Subcategory> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(updateSubcategoryUseCase.update(any(Subcategory.class), any(Long.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        SubcategoryUpdateDTO body = subcategoryUpdateMapper.modelToDto(subcategoryData.getSubcategoryInactive());

        mockMvc.perform(put("/api/v1/subcategory/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateSubcategoryUseCase).update(any(Subcategory.class), any(Long.class));
    }

    @Test
    @Order(11)
    void updateFailedNoChangesException() throws Exception {
        ApiResponse<Subcategory> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoChanges());

        when(updateSubcategoryUseCase.update(any(Subcategory.class), any(Long.class)))
                .thenThrow(new NoChangesException(errorMessages.NO_CHANGES));

        SubcategoryUpdateDTO body = subcategoryUpdateMapper.modelToDto(subcategoryData.getSubcategoryToUpdate());

        mockMvc.perform(put("/api/v1/subcategory/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateSubcategoryUseCase).update(any(Subcategory.class), any(Long.class));
    }

    @Test
    @Order(12)
    void deleteByIdSuccess() throws Exception {
        mockMvc.perform(delete("/api/v1/subcategory/delete/1"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").doesNotExist());

        verify(deleteByIdSubcategoryUseCase).deleteById(any(Long.class));
    }

    @Test
    @Order(13)
    void deleteByIdFailedNonExistenceException() throws Exception {
        ApiResponse<Subcategory> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNonExistence());

        doThrow(new NonExistenceException(errorMessages.NON_EXISTENT_DATA))
                .when(deleteByIdSubcategoryUseCase).deleteById(any(Long.class));

        mockMvc.perform(delete("/api/v1/subcategory/delete/12345"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(deleteByIdSubcategoryUseCase).deleteById(any(Long.class));
    }

    @Test
    @Order(14)
    void changeStateByIdSuccess() throws Exception {
        when(changeStateByIdSubcategoryUseCase.changeStateById(any(Long.class)))
                .thenReturn(subcategoryData.getSubcategoryResponseOne());

        mockMvc.perform(delete("/api/v1/subcategory/change-state/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").value(subcategoryData.getSubcategoryResponseOne().getId()));

        verify(changeStateByIdSubcategoryUseCase).changeStateById(any(Long.class));
    }

    @Test
    @Order(15)
    void changeStateByIdFailedNonExistenceException() throws Exception {
        ApiResponse<Subcategory> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNonExistence());

        when(changeStateByIdSubcategoryUseCase.changeStateById(any(Long.class)))
                .thenThrow(new NonExistenceException(errorMessages.NON_EXISTENT_DATA));

        mockMvc.perform(delete("/api/v1/subcategory/change-state/12345"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(changeStateByIdSubcategoryUseCase).changeStateById(any(Long.class));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}