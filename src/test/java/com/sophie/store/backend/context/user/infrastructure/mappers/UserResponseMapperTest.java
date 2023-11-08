package com.sophie.store.backend.context.user.infrastructure.mappers;

import com.sophie.store.backend.context.user.application.dto.UserResponseDTO;
import com.sophie.store.backend.context.user.data.UserData;
import com.sophie.store.backend.context.user.domain.model.User;
import com.sophie.store.backend.context.user.infrastructure.persistence.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserResponseMapperTest {

    @InjectMocks
    private UserResponseMapper userResponseMapper;

    private static UserData userData;

    @BeforeAll
    static void setUp() {
        userData = new UserData();
    }

    @Test
    void entityToModelTest() {
        UserEntity userEntity = userData.getUserEntity();

        User userModel = userResponseMapper.entityToModel(userEntity);

        assertNotNull(userModel);
    }

    @Test
    void modelToEntityTest() {
        User userModel = userData.getUserModel();

        UserEntity userEntity = userResponseMapper.modelToEntity(userModel);

        assertNotNull(userEntity);
    }

    @Test
    void modelToDtoTest() {
        User userModel = userData.getUserModel();

        UserResponseDTO userResponseDTO = userResponseMapper.modelToDto(userModel);

        assertNotNull(userResponseDTO);
    }

    @Test
    void dtoToModelTest() {
        UserResponseDTO userResponseDTO = userData.getUserResponseDTO();

        User userModel = userResponseMapper.dtoToModel(userResponseDTO);

        assertNotNull(userModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<UserEntity> userEntities = List.of(userData.getUserEntity());

        List<User> userModels = userResponseMapper.entitiesToModels(userEntities);

        assertNotNull(userModels);
        assertEquals(userEntities.size(), userModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<User> userModels = List.of(userData.getUserModel());

        List<UserEntity> userEntities = userResponseMapper.modelsToEntities(userModels);

        assertNotNull(userEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<User> userModels = List.of(userData.getUserModel());

        List<UserResponseDTO> userResponseDTOs = userResponseMapper.modelsToDtos(userModels);

        assertNotNull(userResponseDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<UserResponseDTO> userResponseDTOs = List.of(userData.getUserResponseDTO());

        List<User> userModels = userResponseMapper.dtosToModels(userResponseDTOs);

        assertNotNull(userModels);
    }

}