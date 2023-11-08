package com.sophie.store.backend.context.subcategory.infrastructure.mappers;

import com.sophie.store.backend.context.subcategory.application.dto.SubcategoryCreateDTO;
import com.sophie.store.backend.context.subcategory.data.SubcategoryData;
import com.sophie.store.backend.context.subcategory.domain.model.Subcategory;
import com.sophie.store.backend.context.subcategory.infrastructure.persistence.SubcategoryEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SubcategoryCreateMapperTest {

    @InjectMocks
    private SubcategoryCreateMapper subcategoryCreateMapper;

    private static SubcategoryData subcategoryData;

    @BeforeAll
    static void setUp() {
        subcategoryData = new SubcategoryData();
    }

    @Test
    void entityToModelTest() {
        SubcategoryEntity subcategoryEntity = subcategoryData.getSubcategoryEntity();

        Subcategory subcategoryModel = subcategoryCreateMapper.entityToModel(subcategoryEntity);

        assertNotNull(subcategoryModel);
    }

    @Test
    void modelToEntityTest() {
        Subcategory subcategoryModel = subcategoryData.getSubcategoryModel();

        SubcategoryEntity subcategoryEntity = subcategoryCreateMapper.modelToEntity(subcategoryModel);

        assertNotNull(subcategoryEntity);
    }

    @Test
    void modelToDtoTest() {
        Subcategory subcategoryModel = subcategoryData.getSubcategoryModel();

        SubcategoryCreateDTO subcategoryCreateDTO = subcategoryCreateMapper.modelToDto(subcategoryModel);

        assertNotNull(subcategoryCreateDTO);
    }

    @Test
    void dtoToModelTest() {
        SubcategoryCreateDTO subcategoryCreateDTO = subcategoryData.getSubcategoryCreateDTO();

        Subcategory subcategoryModel = subcategoryCreateMapper.dtoToModel(subcategoryCreateDTO);

        assertNotNull(subcategoryModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<SubcategoryEntity> subcategoryEntities = List.of(subcategoryData.getSubcategoryEntity());

        List<Subcategory> subcategoryModels = subcategoryCreateMapper.entitiesToModels(subcategoryEntities);

        assertNotNull(subcategoryModels);
        assertEquals(subcategoryEntities.size(), subcategoryModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<Subcategory> subcategoryModels = List.of(subcategoryData.getSubcategoryModel());

        List<SubcategoryEntity> subcategoryEntities = subcategoryCreateMapper.modelsToEntities(subcategoryModels);

        assertNotNull(subcategoryEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Subcategory> subcategoryModels = List.of(subcategoryData.getSubcategoryModel());

        List<SubcategoryCreateDTO> subcategoryCreateDTOs = subcategoryCreateMapper.modelsToDtos(subcategoryModels);

        assertNotNull(subcategoryCreateDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<SubcategoryCreateDTO> subcategoryCreateDTOs = List.of(subcategoryData.getSubcategoryCreateDTO());

        List<Subcategory> subcategoryModels = subcategoryCreateMapper.dtosToModels(subcategoryCreateDTOs);

        assertNotNull(subcategoryModels);
    }

}