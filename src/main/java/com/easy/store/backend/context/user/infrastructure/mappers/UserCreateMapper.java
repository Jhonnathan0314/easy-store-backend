package com.easy.store.backend.context.user.infrastructure.mappers;

import com.easy.store.backend.context.roles.infrastructure.mappers.RoleResponseMapper;
import com.easy.store.backend.context.user.application.dto.UserCreateDTO;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserCreateMapper implements Mapper<UserEntity, User, UserCreateDTO> {

    private final RoleResponseMapper roleMapper = new RoleResponseMapper();

    @Override
    public User entityToModel(UserEntity entity) {
        return User.builder()
                .username(entity.getUsername())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .password(entity.getPassword())
                .role(roleMapper.entityToModel(entity.getRole()))
                .build();
    }

    @Override
    public UserEntity modelToEntity(User model) {
        return UserEntity.builder()
                .username(model.getUsername())
                .name(model.getName())
                .lastName(model.getLastName())
                .password(model.getPassword())
                .role(roleMapper.modelToEntity(model.getRole()))
                .build();
    }

    @Override
    public UserCreateDTO modelToDto(User model) {
        return UserCreateDTO.builder()
                .username(model.getUsername())
                .name(model.getName())
                .lastName(model.getLastName())
                .password(model.getPassword())
                .role(roleMapper.modelToDto(model.getRole()))
                .build();
    }

    @Override
    public User dtoToModel(UserCreateDTO dto) {
        return User.builder()
                .username(dto.getUsername())
                .name(dto.getName())
                .lastName(dto.getLastName())
                .password(dto.getPassword())
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
    public List<UserCreateDTO> modelsToDtos(List<User> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> dtosToModels(List<UserCreateDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }
}
