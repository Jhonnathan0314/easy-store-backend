package com.sophie.store.backend.context.user.infrastructure.mappers;

import com.sophie.store.backend.context.roles.infrastructure.mappers.RoleMapper;
import com.sophie.store.backend.context.user.application.dto.UserDTO;
import com.sophie.store.backend.context.user.domain.model.User;
import com.sophie.store.backend.context.user.infrastructure.persistence.UserEntity;
import com.sophie.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper implements Mapper<UserEntity, User, UserDTO> {

    private final RoleMapper roleMapper = new RoleMapper();

    @Override
    public User entityToModel(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .password(entity.getPassword())
                .creationDate(entity.getCreationDate())
                .state(entity.getState())
                .role(roleMapper.entityToModel(entity.getRole()))
                .build();
    }

    @Override
    public UserEntity modelToEntity(User model) {
        return UserEntity.builder()
                .id(model.getId())
                .username(model.getUsername())
                .name(model.getName())
                .lastName(model.getLastName())
                .password(model.getPassword())
                .creationDate(model.getCreationDate())
                .state(model.getState())
                .role(roleMapper.modelToEntity(model.getRole()))
                .build();
    }

    @Override
    public UserDTO modelToDto(User model) {
        return UserDTO.builder()
                .id(model.getId())
                .username(model.getUsername())
                .name(model.getName())
                .lastName(model.getLastName())
                .password(model.getPassword())
                .creationDate(model.getCreationDate())
                .state(model.getState())
                .role(roleMapper.modelToDto(model.getRole()))
                .build();
    }

    @Override
    public User dtoToModel(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .name(dto.getName())
                .lastName(dto.getLastName())
                .password(dto.getPassword())
                .creationDate(dto.getCreationDate())
                .state(dto.getState())
                .role(roleMapper.dtoToModel(dto.getRole()))
                .build();
    }

    @Override
    public List<User> entitiesToModels(List<UserEntity> entities) {
        return entities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserEntity> modelsToEntities(List<User> models) {
        return models.stream()
                .map(this::modelToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> modelsToDtos(List<User> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> dtosToModels(List<UserDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }
}
