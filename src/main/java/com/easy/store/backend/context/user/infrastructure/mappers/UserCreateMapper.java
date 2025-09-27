package com.easy.store.backend.context.user.infrastructure.mappers;

import com.easy.store.backend.context.roles.infrastructure.mappers.RoleResponseMapper;
import com.easy.store.backend.context.user.application.dto.UserCreateDTO;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class UserCreateMapper extends BaseMapper<UserEntity, User, UserCreateDTO> {

    private final RoleResponseMapper roleMapper = new RoleResponseMapper();

    @Override
    public User entityToModel(UserEntity entity) {
        return User.builder()
                .username(entity.getUsername())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .password(entity.getPassword())
                .role(roleMapper.entityToModel(entity.getRole()))
                .state(entity.getState())
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
                .state(model.getState())
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
        User model = User.builder()
                .username(dto.getUsername())
                .name(dto.getName())
                .lastName(dto.getLastName())
                .password(dto.getPassword())
                .build();
        if(dto.getRole() != null) model.setRole(roleMapper.dtoToModel(dto.getRole()));
        return model;
    }

}
