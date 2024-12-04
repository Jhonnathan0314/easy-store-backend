package com.easy.store.backend.context.category.infrastructure.mappers;

import com.easy.store.backend.context.category.application.dto.CategoryUpdateDTO;
import com.easy.store.backend.context.category.data.CategoryData;
import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CategoryUpdateMapperTest {

    @InjectMocks
    private CategoryUpdateMapper categoryUpdateMapper;

    private static CategoryData categoryData;

    @BeforeAll
    static void setUp() {
        categoryData = new CategoryData();
    }

    @Test
    void entityToModelTest() {
        CategoryEntity categoryEntity = categoryData.getCategoryEntity();

        Category categoryModel = categoryUpdateMapper.entityToModel(categoryEntity);

        assertNotNull(categoryModel);
    }

    @Test
    void modelToEntityTest() {
        Category categoryModel = categoryData.getCategoryModel();

        CategoryEntity categoryEntity = categoryUpdateMapper.modelToEntity(categoryModel);

        assertNotNull(categoryEntity);
    }

    @Test
    void modelToDtoTest() {
        Category categoryModel = categoryData.getCategoryModel();

        CategoryUpdateDTO categoryUpdateDTO = categoryUpdateMapper.modelToDto(categoryModel);

        assertNotNull(categoryUpdateDTO);
    }

    @Test
    void dtoToModelTest() {
        CategoryUpdateDTO categoryUpdateDTO = categoryData.getCategoryUpdateDTO();

        Category categoryModel = categoryUpdateMapper.dtoToModel(categoryUpdateDTO);

        assertNotNull(categoryModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<CategoryEntity> categoryEntities = List.of(categoryData.getCategoryEntity());

        List<Category> categoryModels = categoryUpdateMapper.entitiesToModels(categoryEntities);

        assertNotNull(categoryModels);
        assertEquals(categoryEntities.size(), categoryModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<Category> categoryModels = List.of(categoryData.getCategoryModel());

        List<CategoryEntity> categoryEntities = categoryUpdateMapper.modelsToEntities(categoryModels);

        assertNotNull(categoryEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Category> categoryModels = List.of(categoryData.getCategoryModel());

        List<CategoryUpdateDTO> categoryUpdateDTOs = categoryUpdateMapper.modelsToDtos(categoryModels);

        assertNotNull(categoryUpdateDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<CategoryUpdateDTO> categoryUpdateDTOs = List.of(categoryData.getCategoryUpdateDTO());

        List<Category> categoryModels = categoryUpdateMapper.dtosToModels(categoryUpdateDTOs);

        assertNotNull(categoryModels);
    }

}