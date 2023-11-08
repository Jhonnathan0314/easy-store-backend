package com.sophie.store.backend.context.user.infrastructure.mappers;

import com.sophie.store.backend.context.user.application.dto.UserCreateDTO;
import com.sophie.store.backend.context.user.data.UserData;
import com.sophie.store.backend.context.user.domain.model.User;
import com.sophie.store.backend.context.user.infrastructure.persistence.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UserCreateMapperTest {

    @InjectMocks
    private UserCreateMapper userCreateMapper;

    private static UserData userData;

    @BeforeAll
    static void setUp() {
        userData = new UserData();
    }

    @Test
    void entityToModelTest() {
        UserEntity userEntity = userData.getUserEntity();

        User userModel = userCreateMapper.entityToModel(userEntity);

        assertNotNull(userModel);
    }

    @Test
    void modelToEntityTest() {
        User userModel = userData.getUserModel();

        UserEntity userEntity = userCreateMapper.modelToEntity(userModel);

        assertNotNull(userEntity);
    }

    @Test
    void modelToDtoTest() {
        User userModel = userData.getUserModel();

        UserCreateDTO userCreateDTO = userCreateMapper.modelToDto(userModel);

        assertNotNull(userCreateDTO);
    }

    @Test
    void dtoToModelTest() {
        UserCreateDTO userCreateDTO = userData.getUserCreateDTO();

        User userModel = userCreateMapper.dtoToModel(userCreateDTO);

        assertNotNull(userModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<UserEntity> userEntities = List.of(userData.getUserEntity());

        List<User> userModels = userCreateMapper.entitiesToModels(userEntities);

        assertNotNull(userModels);
        assertEquals(userEntities.size(), userModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<User> userModels = List.of(userData.getUserModel());

        List<UserEntity> userEntities = userCreateMapper.modelsToEntities(userModels);

        assertNotNull(userEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<User> userModels = List.of(userData.getUserModel());

        List<UserCreateDTO> userCreateDTOs = userCreateMapper.modelsToDtos(userModels);

        assertNotNull(userCreateDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<UserCreateDTO> userCreateDTOs = List.of(userData.getUserCreateDTO());

        List<User> userModels = userCreateMapper.dtosToModels(userCreateDTOs);

        assertNotNull(userModels);
    }

}