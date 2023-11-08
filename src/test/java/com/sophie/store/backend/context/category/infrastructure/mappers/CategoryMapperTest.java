package com.sophie.store.backend.context.category.infrastructure.mappers;

import com.sophie.store.backend.context.category.application.dto.CategoryDTO;
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
class CategoryMapperTest {

    @InjectMocks
    private CategoryMapper categoryMapper;

    private static CategoryData categoryData;

    @BeforeAll
    static void setUp() {
        categoryData = new CategoryData();
    }

    @Test
    void entityToModelTest() {
        CategoryEntity categoryEntity = categoryData.getCategoryEntity();

        Category categoryModel = categoryMapper.entityToModel(categoryEntity);

        assertNotNull(categoryModel);
    }

    @Test
    void modelToEntityTest() {
        Category categoryModel = categoryData.getCategoryModel();

        CategoryEntity categoryEntity = categoryMapper.modelToEntity(categoryModel);

        assertNotNull(categoryEntity);
    }

    @Test
    void modelToDtoTest() {
        Category categoryModel = categoryData.getCategoryModel();

        CategoryDTO categoryDTO = categoryMapper.modelToDto(categoryModel);

        assertNotNull(categoryDTO);
    }

    @Test
    void dtoToModelTest() {
        CategoryDTO categoryDTO = categoryData.getCategoryDTO();

        Category categoryModel = categoryMapper.dtoToModel(categoryDTO);

        assertNotNull(categoryModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<CategoryEntity> categoryEntities = List.of(categoryData.getCategoryEntity());

        List<Category> categoryModels = categoryMapper.entitiesToModels(categoryEntities);

        assertNotNull(categoryModels);
        assertEquals(categoryEntities.size(), categoryModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<Category> categoryModels = List.of(categoryData.getCategoryModel());

        List<CategoryEntity> categoryEntities = categoryMapper.modelsToEntities(categoryModels);

        assertNotNull(categoryEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Category> categoryModels = List.of(categoryData.getCategoryModel());

        List<CategoryDTO> categoryDTOs = categoryMapper.modelsToDtos(categoryModels);

        assertNotNull(categoryDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<CategoryDTO> categoryDTOs = List.of(categoryData.getCategoryDTO());

        List<Category> categoryModels = categoryMapper.dtosToModels(categoryDTOs);

        assertNotNull(categoryModels);
    }

}