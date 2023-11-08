package com.sophie.store.backend.context.user.infrastructure.mappers;

import com.sophie.store.backend.context.user.application.dto.UserDTO;
import com.sophie.store.backend.context.user.application.dto.UserUpdateDTO;
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
class UserUpdateMapperTest {

    @InjectMocks
    private UserUpdateMapper userUpdateMapper;

    private static UserData userData;

    @BeforeAll
    static void setUp() {
        userData = new UserData();
    }

    @Test
    void entityToModelTest() {
        UserEntity userEntity = userData.getUserEntity();

        User userModel = userUpdateMapper.entityToModel(userEntity);

        assertNotNull(userModel);
    }

    @Test
    void modelToEntityTest() {
        User userModel = userData.getUserModel();

        UserEntity userEntity = userUpdateMapper.modelToEntity(userModel);

        assertNotNull(userEntity);
    }

    @Test
    void modelToDtoTest() {
        User userModel = userData.getUserModel();

        UserUpdateDTO userUpdateDTO = userUpdateMapper.modelToDto(userModel);

        assertNotNull(userUpdateDTO);
    }

    @Test
    void dtoToModelTest() {
        UserUpdateDTO userUpdateDTO = userData.getUserUpdateDTO();

        User userModel = userUpdateMapper.dtoToModel(userUpdateDTO);

        assertNotNull(userModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<UserEntity> userEntities = List.of(userData.getUserEntity());

        List<User> userModels = userUpdateMapper.entitiesToModels(userEntities);

        assertNotNull(userModels);
        assertEquals(userEntities.size(), userModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<User> userModels = List.of(userData.getUserModel());

        List<UserEntity> userEntities = userUpdateMapper.modelsToEntities(userModels);

        assertNotNull(userEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<User> userModels = List.of(userData.getUserModel());

        List<UserUpdateDTO> userUpdateDTOs = userUpdateMapper.modelsToDtos(userModels);

        assertNotNull(userUpdateDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<UserUpdateDTO> userUpdateDTOs = List.of(userData.getUserUpdateDTO());

        List<User> userModels = userUpdateMapper.dtosToModels(userUpdateDTOs);

        assertNotNull(userModels);
    }

}