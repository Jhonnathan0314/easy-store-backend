package com.sophie.store.backend.context.category.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sophie.store.backend.context.category.application.dto.CategoryCreateDTO;
import com.sophie.store.backend.context.category.application.dto.CategoryUpdateDTO;
import com.sophie.store.backend.context.category.application.usecase.*;
import com.sophie.store.backend.context.category.data.CategoryData;
import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.category.infrastructure.mappers.CategoryCreateMapper;
import com.sophie.store.backend.context.category.infrastructure.mappers.CategoryUpdateMapper;
import com.sophie.store.backend.general.GeneralData;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.*;
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
class CategoryControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private FindAllCategoryUseCase findAllCategoryUseCase;

    @Mock
    private FindByIdCategoryUseCase findByIdCategoryUseCase;

    @Mock
    private CreateCategoryUseCase createCategoryUseCase;

    @Mock
    private UpdateCategoryUseCase updateCategoryUseCase;

    @Mock
    private DeleteByIdCategoryUseCase deleteByIdCategoryUseCase;

    @Mock
    private ChangeStateByIdCategoryUseCase changeStateByIdCategoryUseCase;

    @Mock
    private CategoryCreateMapper categoryCreateMapper;

    @Mock
    private CategoryUpdateMapper categoryUpdateMapper;

    private ErrorMessages errorMessages;
    private CategoryData categoryData;
    private GeneralData generalData;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
        categoryData = new CategoryData();
        generalData = new GeneralData();
        errorMessages = new ErrorMessages();
        categoryCreateMapper = new CategoryCreateMapper();
        categoryUpdateMapper = new CategoryUpdateMapper();
    }

    @Test
    @Order(0)
    void findAllSuccess() throws Exception {
        when(findAllCategoryUseCase.findAll()).thenReturn(categoryData.getCategorysList());

        mockMvc.perform(get("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());

        verify(findAllCategoryUseCase).findAll();
    }

    @Test
    @Order(1)
    void findAllFailedNoResultsException() throws Exception {
        ApiResponse<List<Category>> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findAllCategoryUseCase.findAll())
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findAllCategoryUseCase).findAll();
    }

    @Test
    @Order(2)
    void findByIdSuccess() throws Exception {
        when(findByIdCategoryUseCase.findById(any(Long.class))).thenReturn(categoryData.getCategoryResponseOne());

        mockMvc.perform(get("/api/v1/category/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").value(categoryData.getCategoryResponseOne()));

        verify(findByIdCategoryUseCase).findById(any(Long.class));
    }

    @Test
    @Order(3)
    void findByIdFailedNoResultsException() throws Exception {
        ApiResponse<Category> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findByIdCategoryUseCase.findById(any(Long.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/category/12345")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findByIdCategoryUseCase).findById(any(Long.class));
    }

    @Test
    @Order(4)
    void createSuccess() throws Exception {
        when(createCategoryUseCase.create(any(Category.class))).thenReturn(categoryData.getCategoryResponseOne());

        CategoryCreateDTO body = categoryCreateMapper.modelToDto(categoryData.getCategoryCreateValid());

        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Create-By", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").value(categoryData.getCategoryResponseOne()));

        verify(createCategoryUseCase).create(any(Category.class));
    }

    @Test
    @Order(5)
    void createFailedDuplicatedException() throws Exception {
        ApiResponse<Category> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorDuplicated());

        when(createCategoryUseCase.create(any(Category.class)))
                .thenThrow(new DuplicatedException(errorMessages.DUPLICATED));

        CategoryCreateDTO body = categoryCreateMapper.modelToDto(categoryData.getCategoryCreateValid());

        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Create-By", 1))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(createCategoryUseCase).create(any(Category.class));
    }

    @Test
    @Order(6)
    void createFailedInvalidBodyException() throws Exception {
        ApiResponse<Category> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorInvalidBody());

        when(createCategoryUseCase.create(any(Category.class)))
                .thenThrow(new InvalidBodyException(errorMessages.INVALID_BODY));

        CategoryCreateDTO body = categoryCreateMapper.modelToDto(categoryData.getCategoryCreateValid());

        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Create-By", 1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(createCategoryUseCase).create(any(Category.class));
    }

    @Test
    @Order(7)
    void updateSuccess() throws Exception {
        when(updateCategoryUseCase.update(any(Category.class))).thenReturn(categoryData.getCategoryResponseOne());

        CategoryUpdateDTO body = categoryUpdateMapper.modelToDto(categoryData.getCategoryToUpdate());

        mockMvc.perform(put("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").value(categoryData.getCategoryResponseOne()));

        verify(updateCategoryUseCase).update(any(Category.class));
    }

    @Test
    @Order(8)
    void updateFailedNoIdReceivedException() throws Exception {
        ApiResponse<Category> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoIdReceived());

        when(updateCategoryUseCase.update(any(Category.class)))
                .thenThrow(new NoIdReceivedException(errorMessages.NO_ID_RECEIVED));

        CategoryUpdateDTO body = categoryUpdateMapper.modelToDto(categoryData.getCategoryToUpdateNoId());

        mockMvc.perform(put("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateCategoryUseCase).update(any(Category.class));
    }

    @Test
    @Order(9)
    void updateFailedInvalidBodyException() throws Exception {
        ApiResponse<Category> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorInvalidBody());

        when(updateCategoryUseCase.update(any(Category.class)))
                .thenThrow(new InvalidBodyException(errorMessages.INVALID_BODY));

        CategoryUpdateDTO body = categoryUpdateMapper.modelToDto(categoryData.getCategoryToUpdateInvalid());

        mockMvc.perform(put("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateCategoryUseCase).update(any(Category.class));
    }

    @Test
    @Order(10)
    void updateFailedNoResultsException() throws Exception {
        ApiResponse<Category> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(updateCategoryUseCase.update(any(Category.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        CategoryUpdateDTO body = categoryUpdateMapper.modelToDto(categoryData.getCategoryInactive());

        mockMvc.perform(put("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateCategoryUseCase).update(any(Category.class));
    }

    @Test
    @Order(11)
    void updateFailedNoChangesException() throws Exception {
        ApiResponse<Category> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoChanges());

        when(updateCategoryUseCase.update(any(Category.class)))
                .thenThrow(new NoChangesException(errorMessages.NO_CHANGES));

        CategoryUpdateDTO body = categoryUpdateMapper.modelToDto(categoryData.getCategoryToUpdate());

        mockMvc.perform(put("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateCategoryUseCase).update(any(Category.class));
    }

    @Test
    @Order(12)
    void deleteByIdSuccess() throws Exception {
        mockMvc.perform(delete("/api/v1/category/delete/1"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").doesNotExist());

        verify(deleteByIdCategoryUseCase).deleteById(any(Long.class));
    }

    @Test
    @Order(13)
    void deleteByIdFailedNonExistenceException() throws Exception {
        ApiResponse<Category> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNonExistence());

        doThrow(new NonExistenceException(errorMessages.NON_EXISTENT_DATA))
                .when(deleteByIdCategoryUseCase).deleteById(any(Long.class));

        mockMvc.perform(delete("/api/v1/category/delete/12345"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(deleteByIdCategoryUseCase).deleteById(any(Long.class));
    }

    @Test
    @Order(14)
    void changeStateByIdSuccess() throws Exception {
        when(changeStateByIdCategoryUseCase.changeStateById(any(Long.class)))
                .thenReturn(categoryData.getCategoryResponseOne());

        mockMvc.perform(delete("/api/v1/category/change-state/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").value(categoryData.getCategoryResponseOne()));

        verify(changeStateByIdCategoryUseCase).changeStateById(any(Long.class));
    }

    @Test
    @Order(15)
    void changeStateByIdFailedNonExistenceException() throws Exception {
        ApiResponse<Category> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNonExistence());

        when(changeStateByIdCategoryUseCase.changeStateById(any(Long.class)))
                .thenThrow(new NonExistenceException(errorMessages.NON_EXISTENT_DATA));

        mockMvc.perform(delete("/api/v1/category/change-state/12345"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(changeStateByIdCategoryUseCase).changeStateById(any(Long.class));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}