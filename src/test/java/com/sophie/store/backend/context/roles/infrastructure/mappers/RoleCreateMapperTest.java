package com.sophie.store.backend.context.roles.infrastructure.mappers;

import com.sophie.store.backend.context.roles.application.dto.RoleCreateDTO;
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
class RoleCreateMapperTest {

    @InjectMocks
    private RoleCreateMapper roleCreateMapper;

    private static RoleData roleData;

    @BeforeAll
    static void setUp() {
        roleData = new RoleData();
    }

    @Test
    void entityToModelTest() {
        RoleEntity roleEntity = roleData.getRoleEntity();

        Role roleModel = roleCreateMapper.entityToModel(roleEntity);

        assertNotNull(roleModel);
    }

    @Test
    void modelToEntityTest() {
        Role roleModel = roleData.getRoleModel();

        RoleEntity roleEntity = roleCreateMapper.modelToEntity(roleModel);

        assertNotNull(roleEntity);
    }

    @Test
    void modelToDtoTest() {
        Role roleModel = roleData.getRoleModel();

        RoleCreateDTO roleCreateDTO = roleCreateMapper.modelToDto(roleModel);

        assertNotNull(roleCreateDTO);
    }

    @Test
    void dtoToModelTest() {
        RoleCreateDTO roleCreateDTO = roleData.getRoleCreateDTO();

        Role roleModel = roleCreateMapper.dtoToModel(roleCreateDTO);

        assertNotNull(roleModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<RoleEntity> roleEntities = List.of(roleData.getRoleEntity());

        List<Role> roleModels = roleCreateMapper.entitiesToModels(roleEntities);

        assertNotNull(roleModels);
        assertEquals(roleEntities.size(), roleModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<Role> roleModels = List.of(roleData.getRoleModel());

        List<RoleEntity> roleEntities = roleCreateMapper.modelsToEntities(roleModels);

        assertNotNull(roleEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Role> roleModels = List.of(roleData.getRoleModel());

        List<RoleCreateDTO> roleCreateDTOs = roleCreateMapper.modelsToDtos(roleModels);

        assertNotNull(roleCreateDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<RoleCreateDTO> roleCreateDTOs = List.of(roleData.getRoleCreateDTO());

        List<Role> roleModels = roleCreateMapper.dtosToModels(roleCreateDTOs);

        assertNotNull(roleModels);
    }

}