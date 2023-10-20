package com.sophie.store.backend.context.user.infrastructure.mappers;

import com.sophie.store.backend.context.roles.infrastructure.mappers.RoleResponseMapper;
import com.sophie.store.backend.context.user.application.dto.UserResponseDTO;
import com.sophie.store.backend.context.user.domain.model.User;
import com.sophie.store.backend.context.user.infrastructure.persistence.UserEntity;
import com.sophie.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserResponseMapper implements Mapper<UserEntity, User, UserResponseDTO> {

    private final RoleResponseMapper roleResponseMapper = new RoleResponseMapper();

    @Override
    public User entityToModel(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .role(roleResponseMapper.entityToModel(entity.getRole()))
                .build();
    }

    @Override
    public UserEntity modelToEntity(User model) {
        return UserEntity.builder()
                .id(model.getId())
                .username(model.getUsername())
                .name(model.getName())
                .lastName(model.getLastName())
                .role(roleResponseMapper.modelToEntity(model.getRole()))
                .build();
    }

    @Override
    public UserResponseDTO modelToDto(User model) {
        return UserResponseDTO.builder()
                .id(model.getId())
                .username(model.getUsername())
                .name(model.getName())
                .lastName(model.getLastName())
                .role(roleResponseMapper.modelToDto(model.getRole()))
                .build();
    }

    @Override
    public User dtoToModel(UserResponseDTO dto) {
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .name(dto.getName())
                .lastName(dto.getLastName())
                .role(roleResponseMapper.dtoToModel(dto.getRole()))
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
    public List<UserResponseDTO> modelsToDtos(List<User> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> dtosToModels(List<UserResponseDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }
}
