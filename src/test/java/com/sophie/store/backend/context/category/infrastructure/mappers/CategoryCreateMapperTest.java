package com.sophie.store.backend.context.category.infrastructure.mappers;

import com.sophie.store.backend.context.category.application.dto.CategoryCreateDTO;
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
class CategoryCreateMapperTest {

    @InjectMocks
    private CategoryCreateMapper categoryCreateMapper;

    private static CategoryData categoryData;

    @BeforeAll
    static void setUp() {
        categoryData = new CategoryData();
    }

    @Test
    void entityToModelTest() {
        CategoryEntity categoryEntity = categoryData.getCategoryEntity();

        Category categoryModel = categoryCreateMapper.entityToModel(categoryEntity);

        assertNotNull(categoryModel);
    }

    @Test
    void modelToEntityTest() {
        Category categoryModel = categoryData.getCategoryModel();

        CategoryEntity categoryEntity = categoryCreateMapper.modelToEntity(categoryModel);

        assertNotNull(categoryEntity);
    }

    @Test
    void modelToDtoTest() {
        Category categoryModel = categoryData.getCategoryModel();

        CategoryCreateDTO categoryCreateDTO = categoryCreateMapper.modelToDto(categoryModel);

        assertNotNull(categoryCreateDTO);
    }

    @Test
    void dtoToModelTest() {
        CategoryCreateDTO categoryCreateDTO = categoryData.getCategoryCreateDTO();

        Category categoryModel = categoryCreateMapper.dtoToModel(categoryCreateDTO);

        assertNotNull(categoryModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<CategoryEntity> categoryEntities = List.of(categoryData.getCategoryEntity());

        List<Category> categoryModels = categoryCreateMapper.entitiesToModels(categoryEntities);

        assertNotNull(categoryModels);
        assertEquals(categoryEntities.size(), categoryModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<Category> categoryModels = List.of(categoryData.getCategoryModel());

        List<CategoryEntity> categoryEntities = categoryCreateMapper.modelsToEntities(categoryModels);

        assertNotNull(categoryEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Category> categoryModels = List.of(categoryData.getCategoryModel());

        List<CategoryCreateDTO> categoryCreateDTOs = categoryCreateMapper.modelsToDtos(categoryModels);

        assertNotNull(categoryCreateDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<CategoryCreateDTO> categoryCreateDTOs = List.of(categoryData.getCategoryCreateDTO());

        List<Category> categoryModels = categoryCreateMapper.dtosToModels(categoryCreateDTOs);

        assertNotNull(categoryModels);
    }

}