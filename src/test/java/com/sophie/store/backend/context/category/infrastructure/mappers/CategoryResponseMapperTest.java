package com.sophie.store.backend.context.category.infrastructure.mappers;

import com.sophie.store.backend.context.category.application.dto.CategoryResponseDTO;
import com.sophie.store.backend.context.category.data.CategoryData;
import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CategoryResponseMapperTest {

    @InjectMocks
    private CategoryResponseMapper categoryResponseMapper;

    private static CategoryData categoryData;

    @BeforeAll
    static void setUp() {
        categoryData = new CategoryData();
    }

    @Test
    void entityToModelTest() {
        CategoryEntity categoryEntity = categoryData.getCategoryEntity();

        Category categoryModel = categoryResponseMapper.entityToModel(categoryEntity);

        assertNotNull(categoryModel);
    }

    @Test
    void modelToEntityTest() {
        Category categoryModel = categoryData.getCategoryModel();

        CategoryEntity categoryEntity = categoryResponseMapper.modelToEntity(categoryModel);

        assertNotNull(categoryEntity);
    }

    @Test
    void modelToDtoTest() {
        Category categoryModel = categoryData.getCategoryModel();

        CategoryResponseDTO categoryResponseDTO = categoryResponseMapper.modelToDto(categoryModel);

        assertNotNull(categoryResponseDTO);
    }

    @Test
    void dtoToModelTest() {
        CategoryResponseDTO categoryResponseDTO = categoryData.getCategoryResponseDTO();

        Category categoryModel = categoryResponseMapper.dtoToModel(categoryResponseDTO);

        assertNotNull(categoryModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<CategoryEntity> categoryEntities = List.of(categoryData.getCategoryEntity());

        List<Category> categoryModels = categoryResponseMapper.entitiesToModels(categoryEntities);

        assertNotNull(categoryModels);
        assertEquals(categoryEntities.size(), categoryModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<Category> categoryModels = List.of(categoryData.getCategoryModel());

        List<CategoryEntity> categoryEntities = categoryResponseMapper.modelsToEntities(categoryModels);

        assertNotNull(categoryEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Category> categoryModels = List.of(categoryData.getCategoryModel());

        List<CategoryResponseDTO> categoryResponseDTOs = categoryResponseMapper.modelsToDtos(categoryModels);

        assertNotNull(categoryResponseDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<CategoryResponseDTO> categoryResponseDTOs = List.of(categoryData.getCategoryResponseDTO());

        List<Category> categoryModels = categoryResponseMapper.dtosToModels(categoryResponseDTOs);

        assertNotNull(categoryModels);
    }

}