package com.easy.store.backend.context.user.infrastructure.mappers;

import com.easy.store.backend.context.roles.infrastructure.mappers.RoleResponseMapper;
import com.easy.store.backend.context.user.application.dto.UserResponseDTO;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class UserResponseMapper extends BaseMapper<UserEntity, User, UserResponseDTO> {

    private final RoleResponseMapper roleResponseMapper = new RoleResponseMapper();

    @Override
    public User entityToModel(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .role(roleResponseMapper.entityToModel(entity.getRole()))
                .state(entity.getState())
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
                .state(model.getState())
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

}
