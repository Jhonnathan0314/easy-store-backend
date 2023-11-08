package com.sophie.store.backend.context.roles.infrastructure.mappers;

import com.sophie.store.backend.context.roles.application.dto.RoleResponseDTO;
import com.sophie.store.backend.context.roles.data.RoleData;
import com.sophie.store.backend.context.roles.domain.model.Role;
import com.sophie.store.backend.context.roles.infrastructure.persistence.RoleEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class RoleResponseMapperTest {

    @InjectMocks
    private RoleResponseMapper roleResponseMapper;

    private static RoleData roleData;

    @BeforeAll
    static void setUp() {
        roleData = new RoleData();
    }

    @Test
    void entityToModelTest() {
        RoleEntity roleEntity = roleData.getRoleEntity();

        Role roleModel = roleResponseMapper.entityToModel(roleEntity);

        assertNotNull(roleModel);
    }

    @Test
    void modelToEntityTest() {
        Role roleModel = roleData.getRoleModel();

        RoleEntity roleEntity = roleResponseMapper.modelToEntity(roleModel);

        assertNotNull(roleEntity);
    }

    @Test
    void modelToDtoTest() {
        Role roleModel = roleData.getRoleModel();

        RoleResponseDTO roleResponseDTO = roleResponseMapper.modelToDto(roleModel);

        assertNotNull(roleResponseDTO);
    }

    @Test
    void dtoToModelTest() {
        RoleResponseDTO roleResponseDTO = roleData.getRoleResponseDTO();

        Role roleModel = roleResponseMapper.dtoToModel(roleResponseDTO);

        assertNotNull(roleModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<RoleEntity> roleEntities = List.of(roleData.getRoleEntity());

        List<Role> roleModels = roleResponseMapper.entitiesToModels(roleEntities);

        assertNotNull(roleModels);
        assertEquals(roleEntities.size(), roleModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<Role> roleModels = List.of(roleData.getRoleModel());

        List<RoleEntity> roleEntities = roleResponseMapper.modelsToEntities(roleModels);

        assertNotNull(roleEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Role> roleModels = List.of(roleData.getRoleModel());

        List<RoleResponseDTO> roleResponseDTOs = roleResponseMapper.modelsToDtos(roleModels);

        assertNotNull(roleResponseDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<RoleResponseDTO> roleResponseDTOs = List.of(roleData.getRoleResponseDTO());

        List<Role> roleModels = roleResponseMapper.dtosToModels(roleResponseDTOs);

        assertNotNull(roleModels);
    }

}