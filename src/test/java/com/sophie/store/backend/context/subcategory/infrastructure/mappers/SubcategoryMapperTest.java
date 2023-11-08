package com.sophie.store.backend.context.subcategory.infrastructure.mappers;

import com.sophie.store.backend.context.subcategory.application.dto.SubcategoryDTO;
import com.sophie.store.backend.context.subcategory.data.SubcategoryData;
import com.sophie.store.backend.context.subcategory.domain.model.Subcategory;
import com.sophie.store.backend.context.subcategory.infrastructure.persistence.SubcategoryEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SubcategoryMapperTest {

    @InjectMocks
    private SubcategoryMapper subcategoryMapper;

    private static SubcategoryData subcategoryData;

    @BeforeAll
    static void setUp() {
        subcategoryData = new SubcategoryData();
    }

    @Test
    void entityToModelTest() {
        SubcategoryEntity subcategoryEntity = subcategoryData.getSubcategoryEntity();

        Subcategory subcategoryModel = subcategoryMapper.entityToModel(subcategoryEntity);

        assertNotNull(subcategoryModel);
    }

    @Test
    void modelToEntityTest() {
        Subcategory subcategoryModel = subcategoryData.getSubcategoryModel();

        SubcategoryEntity subcategoryEntity = subcategoryMapper.modelToEntity(subcategoryModel);

        assertNotNull(subcategoryEntity);
    }

    @Test
    void modelToDtoTest() {
        Subcategory subcategoryModel = subcategoryData.getSubcategoryModel();

        SubcategoryDTO subcategoryDTO = subcategoryMapper.modelToDto(subcategoryModel);

        assertNotNull(subcategoryDTO);
    }

    @Test
    void dtoToModelTest() {
        SubcategoryDTO subcategoryDTO = subcategoryData.getSubcategoryDTO();

        Subcategory subcategoryModel = subcategoryMapper.dtoToModel(subcategoryDTO);

        assertNotNull(subcategoryModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<SubcategoryEntity> subcategoryEntities = List.of(subcategoryData.getSubcategoryEntity());

        List<Subcategory> subcategoryModels = subcategoryMapper.entitiesToModels(subcategoryEntities);

        assertNotNull(subcategoryModels);
    }

    @Test
    void modelsToEntitiesTest() {
        List<Subcategory> subcategoryModels = List.of(subcategoryData.getSubcategoryModel());

        List<SubcategoryEntity> subcategoryEntities = subcategoryMapper.modelsToEntities(subcategoryModels);

        assertNotNull(subcategoryEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Subcategory> subcategoryModels = List.of(subcategoryData.getSubcategoryModel());

        List<SubcategoryDTO> subcategoryDTOs = subcategoryMapper.modelsToDtos(subcategoryModels);

        assertNotNull(subcategoryDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<SubcategoryDTO> subcategoryDTOs = List.of(subcategoryData.getSubcategoryDTO());

        List<Subcategory> subcategoryModels = subcategoryMapper.dtosToModels(subcategoryDTOs);

        assertNotNull(subcategoryModels);
    }

}