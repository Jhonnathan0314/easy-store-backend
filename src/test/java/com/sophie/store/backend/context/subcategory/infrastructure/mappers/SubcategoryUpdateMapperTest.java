package com.sophie.store.backend.context.subcategory.infrastructure.mappers;

import com.sophie.store.backend.context.subcategory.application.dto.SubcategoryUpdateDTO;
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
class SubcategoryUpdateMapperTest {

    @InjectMocks
    private SubcategoryUpdateMapper subcategoryUpdateMapper;

    private static SubcategoryData subcategoryData;

    @BeforeAll
    static void setUp() {
        subcategoryData = new SubcategoryData();
    }

    @Test
    void entityToModelTest() {
        SubcategoryEntity subcategoryEntity = subcategoryData.getSubcategoryEntity();

        Subcategory subcategoryModel = subcategoryUpdateMapper.entityToModel(subcategoryEntity);

        assertNotNull(subcategoryModel);
    }

    @Test
    void modelToEntityTest() {
        Subcategory subcategoryModel = subcategoryData.getSubcategoryModel();

        SubcategoryEntity subcategoryEntity = subcategoryUpdateMapper.modelToEntity(subcategoryModel);

        assertNotNull(subcategoryEntity);
    }

    @Test
    void modelToDtoTest() {
        Subcategory subcategoryModel = subcategoryData.getSubcategoryModel();

        SubcategoryUpdateDTO subcategoryUpdateDTO = subcategoryUpdateMapper.modelToDto(subcategoryModel);

        assertNotNull(subcategoryUpdateDTO);
    }

    @Test
    void dtoToModelTest() {
        SubcategoryUpdateDTO subcategoryUpdateDTO = subcategoryData.getSubcategoryUpdateDTO();

        Subcategory subcategoryModel = subcategoryUpdateMapper.dtoToModel(subcategoryUpdateDTO);

        assertNotNull(subcategoryModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<SubcategoryEntity> subcategoryEntities = List.of(subcategoryData.getSubcategoryEntity());

        List<Subcategory> subcategoryModels = subcategoryUpdateMapper.entitiesToModels(subcategoryEntities);

        assertNotNull(subcategoryModels);
        assertEquals(subcategoryEntities.size(), subcategoryModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<Subcategory> subcategoryModels = List.of(subcategoryData.getSubcategoryModel());

        List<SubcategoryEntity> subcategoryEntities = subcategoryUpdateMapper.modelsToEntities(subcategoryModels);

        assertNotNull(subcategoryEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Subcategory> subcategoryModels = List.of(subcategoryData.getSubcategoryModel());

        List<SubcategoryUpdateDTO> subcategoryUpdateDTOs = subcategoryUpdateMapper.modelsToDtos(subcategoryModels);

        assertNotNull(subcategoryUpdateDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<SubcategoryUpdateDTO> subcategoryUpdateDTOs = List.of(subcategoryData.getSubcategoryUpdateDTO());

        List<Subcategory> subcategoryModels = subcategoryUpdateMapper.dtosToModels(subcategoryUpdateDTOs);

        assertNotNull(subcategoryModels);
    }

}