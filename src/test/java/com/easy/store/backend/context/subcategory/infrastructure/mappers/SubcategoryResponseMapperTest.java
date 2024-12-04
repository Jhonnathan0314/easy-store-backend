package com.easy.store.backend.context.subcategory.infrastructure.mappers;

import com.easy.store.backend.context.subcategory.application.dto.SubcategoryResponseDTO;
import com.easy.store.backend.context.subcategory.data.SubcategoryData;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.infrastructure.persistence.SubcategoryEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SubcategoryResponseMapperTest {

    @InjectMocks
    private SubcategoryResponseMapper subcategoryResponseMapper;

    private static SubcategoryData subcategoryData;

    @BeforeAll
    static void setUp() {
        subcategoryData = new SubcategoryData();
    }

    @Test
    void entityToModelTest() {
        SubcategoryEntity subcategoryEntity = subcategoryData.getSubcategoryEntity();

        Subcategory subcategoryModel = subcategoryResponseMapper.entityToModel(subcategoryEntity);

        assertNotNull(subcategoryModel);
    }

    @Test
    void modelToEntityTest() {
        Subcategory subcategoryModel = subcategoryData.getSubcategoryModel();

        SubcategoryEntity subcategoryEntity = subcategoryResponseMapper.modelToEntity(subcategoryModel);

        assertNotNull(subcategoryEntity);
    }

    @Test
    void modelToDtoTest() {
        Subcategory subcategoryModel = subcategoryData.getSubcategoryModel();

        SubcategoryResponseDTO subcategoryResponseDTO = subcategoryResponseMapper.modelToDto(subcategoryModel);

        assertNotNull(subcategoryResponseDTO);
    }

    @Test
    void dtoToModelTest() {
        SubcategoryResponseDTO subcategoryResponseDTO = subcategoryData.getSubcategoryResponseDTO();

        Subcategory subcategoryModel = subcategoryResponseMapper.dtoToModel(subcategoryResponseDTO);

        assertNotNull(subcategoryModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<SubcategoryEntity> subcategoryEntities = List.of(subcategoryData.getSubcategoryEntity());

        List<Subcategory> subcategoryModels = subcategoryResponseMapper.entitiesToModels(subcategoryEntities);

        assertNotNull(subcategoryModels);
        assertEquals(subcategoryEntities.size(), subcategoryModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<Subcategory> subcategoryModels = List.of(subcategoryData.getSubcategoryModel());

        List<SubcategoryEntity> subcategoryEntities = subcategoryResponseMapper.modelsToEntities(subcategoryModels);

        assertNotNull(subcategoryEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Subcategory> subcategoryModels = List.of(subcategoryData.getSubcategoryModel());

        List<SubcategoryResponseDTO> subcategoryResponseDTOs = subcategoryResponseMapper.modelsToDtos(subcategoryModels);

        assertNotNull(subcategoryResponseDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<SubcategoryResponseDTO> subcategoryResponseDTOs = List.of(subcategoryData.getSubcategoryResponseDTO());

        List<Subcategory> subcategoryModels = subcategoryResponseMapper.dtosToModels(subcategoryResponseDTOs);

        assertNotNull(subcategoryModels);
    }

}