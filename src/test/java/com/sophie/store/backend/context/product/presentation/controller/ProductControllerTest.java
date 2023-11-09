package com.sophie.store.backend.context.product.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sophie.store.backend.context.product.application.dto.ProductCreateDTO;
import com.sophie.store.backend.context.product.application.dto.ProductUpdateDTO;
import com.sophie.store.backend.context.product.application.usecase.*;
import com.sophie.store.backend.context.product.data.ProductData;
import com.sophie.store.backend.context.product.domain.model.Product;
import com.sophie.store.backend.context.product.infrastructure.mappers.ProductCreateMapper;
import com.sophie.store.backend.context.product.infrastructure.mappers.ProductUpdateMapper;
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
class ProductControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ProductController productController;

    @Mock
    private FindAllProductUseCase findAllProductUseCase;

    @Mock
    private FindByIdProductUseCase findByIdProductUseCase;

    @Mock
    private CreateProductUseCase createProductUseCase;

    @Mock
    private UpdateProductUseCase updateProductUseCase;

    @Mock
    private DeleteByIdProductUseCase deleteByIdProductUseCase;

    @Mock
    private ChangeStateByIdProductUseCase changeStateByIdProductUseCase;

    @Mock
    private ProductCreateMapper productCreateMapper;

    @Mock
    private ProductUpdateMapper productUpdateMapper;

    private ErrorMessages errorMessages;
    private ProductData productData;
    private GeneralData generalData;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        productData = new ProductData();
        generalData = new GeneralData();
        errorMessages = new ErrorMessages();
        productCreateMapper = new ProductCreateMapper();
        productUpdateMapper = new ProductUpdateMapper();
    }

    @Test
    @Order(0)
    void findAllSuccess() throws Exception {
        when(findAllProductUseCase.findAll()).thenReturn(productData.getProductsList());

        mockMvc.perform(get("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());

        verify(findAllProductUseCase).findAll();
    }

    @Test
    @Order(1)
    void findAllFailedNoResultsException() throws Exception {
        ApiResponse<List<Product>> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findAllProductUseCase.findAll())
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findAllProductUseCase).findAll();
    }

    @Test
    @Order(2)
    void findByIdSuccess() throws Exception {
        when(findByIdProductUseCase.findById(any(Long.class))).thenReturn(productData.getProductResponseOne());

        mockMvc.perform(get("/api/v1/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").value(productData.getProductResponseOne().getId()))
                .andExpect(jsonPath("$.data.subcategory.id").value(productData.getProductResponseOne().getSubcategory().getId()))
                .andExpect(jsonPath("$.data.subcategory.category.id").value(productData.getProductResponseOne().getSubcategory().getCategory().getId()));

        verify(findByIdProductUseCase).findById(any(Long.class));
    }

    @Test
    @Order(3)
    void findByIdFailedNoResultsException() throws Exception {
        ApiResponse<Product> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findByIdProductUseCase.findById(any(Long.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/product/12345")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findByIdProductUseCase).findById(any(Long.class));
    }

    @Test
    @Order(4)
    void createSuccess() throws Exception {
        when(createProductUseCase.create(any(Product.class), any(Long.class))).thenReturn(productData.getProductResponseOne());

        ProductCreateDTO body = productCreateMapper.modelToDto(productData.getProductCreateValid());

        mockMvc.perform(post("/api/v1/product/subcategory/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Create-By", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").value(productData.getProductResponseOne().getId()))
                .andExpect(jsonPath("$.data.subcategory.id").value(productData.getProductResponseOne().getSubcategory().getId()))
                .andExpect(jsonPath("$.data.subcategory.category.id").value(productData.getProductResponseOne().getSubcategory().getCategory().getId()));

        verify(createProductUseCase).create(any(Product.class), any(Long.class));
    }

    @Test
    @Order(5)
    void createFailedDuplicatedException() throws Exception {
        ApiResponse<Product> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorDuplicated());

        when(createProductUseCase.create(any(Product.class), any(Long.class)))
                .thenThrow(new DuplicatedException(errorMessages.DUPLICATED));

        ProductCreateDTO body = productCreateMapper.modelToDto(productData.getProductCreateValid());

        mockMvc.perform(post("/api/v1/product/subcategory/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Create-By", 1))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(createProductUseCase).create(any(Product.class), any(Long.class));
    }

    @Test
    @Order(6)
    void createFailedInvalidBodyException() throws Exception {
        ApiResponse<Product> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorInvalidBody());

        when(createProductUseCase.create(any(Product.class), any(Long.class)))
                .thenThrow(new InvalidBodyException(errorMessages.INVALID_BODY));

        ProductCreateDTO body = productCreateMapper.modelToDto(productData.getProductCreateValid());

        mockMvc.perform(post("/api/v1/product/subcategory/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Create-By", 1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(createProductUseCase).create(any(Product.class), any(Long.class));
    }

    @Test
    @Order(7)
    void updateSuccess() throws Exception {
        when(updateProductUseCase.update(any(Product.class), any(Long.class))).thenReturn(productData.getProductResponseOne());

        ProductUpdateDTO body = productUpdateMapper.modelToDto(productData.getProductToUpdate());

        mockMvc.perform(put("/api/v1/product/subcategory/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").value(productData.getProductResponseOne().getId()))
                .andExpect(jsonPath("$.data.subcategory.id").value(productData.getProductResponseOne().getSubcategory().getId()))
                .andExpect(jsonPath("$.data.subcategory.category.id").value(productData.getProductResponseOne().getSubcategory().getCategory().getId()));

        verify(updateProductUseCase).update(any(Product.class), any(Long.class));
    }

    @Test
    @Order(8)
    void updateFailedNoIdReceivedException() throws Exception {
        ApiResponse<Product> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoIdReceived());

        when(updateProductUseCase.update(any(Product.class), any(Long.class)))
                .thenThrow(new NoIdReceivedException(errorMessages.NO_ID_RECEIVED));

        ProductUpdateDTO body = productUpdateMapper.modelToDto(productData.getProductToUpdateNoId());

        mockMvc.perform(put("/api/v1/product/subcategory/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateProductUseCase).update(any(Product.class), any(Long.class));
    }

    @Test
    @Order(9)
    void updateFailedInvalidBodyException() throws Exception {
        ApiResponse<Product> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorInvalidBody());

        when(updateProductUseCase.update(any(Product.class), any(Long.class)))
                .thenThrow(new InvalidBodyException(errorMessages.INVALID_BODY));

        ProductUpdateDTO body = productUpdateMapper.modelToDto(productData.getProductToUpdateInvalid());

        mockMvc.perform(put("/api/v1/product/subcategory/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateProductUseCase).update(any(Product.class), any(Long.class));
    }

    @Test
    @Order(10)
    void updateFailedNoResultsException() throws Exception {
        ApiResponse<Product> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(updateProductUseCase.update(any(Product.class), any(Long.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        ProductUpdateDTO body = productUpdateMapper.modelToDto(productData.getProductInactive());

        mockMvc.perform(put("/api/v1/product/subcategory/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateProductUseCase).update(any(Product.class), any(Long.class));
    }

    @Test
    @Order(11)
    void updateFailedNoChangesException() throws Exception {
        ApiResponse<Product> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoChanges());

        when(updateProductUseCase.update(any(Product.class), any(Long.class)))
                .thenThrow(new NoChangesException(errorMessages.NO_CHANGES));

        ProductUpdateDTO body = productUpdateMapper.modelToDto(productData.getProductToUpdate());

        mockMvc.perform(put("/api/v1/product/subcategory/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body))
                        .header("Update-By", 1))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateProductUseCase).update(any(Product.class), any(Long.class));
    }

    @Test
    @Order(12)
    void deleteByIdSuccess() throws Exception {
        mockMvc.perform(delete("/api/v1/product/delete/1"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").doesNotExist());

        verify(deleteByIdProductUseCase).deleteById(any(Long.class));
    }

    @Test
    @Order(13)
    void deleteByIdFailedNonExistenceException() throws Exception {
        ApiResponse<Product> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNonExistence());

        doThrow(new NonExistenceException(errorMessages.NON_EXISTENT_DATA))
                .when(deleteByIdProductUseCase).deleteById(any(Long.class));

        mockMvc.perform(delete("/api/v1/product/delete/12345"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(deleteByIdProductUseCase).deleteById(any(Long.class));
    }

    @Test
    @Order(14)
    void changeStateByIdSuccess() throws Exception {
        when(changeStateByIdProductUseCase.changeStateById(any(Long.class)))
                .thenReturn(productData.getProductResponseOne());

        mockMvc.perform(delete("/api/v1/product/change-state/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").value(productData.getProductResponseOne().getId()));

        verify(changeStateByIdProductUseCase).changeStateById(any(Long.class));
    }

    @Test
    @Order(15)
    void changeStateByIdFailedNonExistenceException() throws Exception {
        ApiResponse<Product> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNonExistence());

        when(changeStateByIdProductUseCase.changeStateById(any(Long.class)))
                .thenThrow(new NonExistenceException(errorMessages.NON_EXISTENT_DATA));

        mockMvc.perform(delete("/api/v1/product/change-state/12345"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(changeStateByIdProductUseCase).changeStateById(any(Long.class));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}