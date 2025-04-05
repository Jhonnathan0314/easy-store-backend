package com.easy.store.backend.context.user.infrastructure.mappers;

import com.easy.store.backend.context.roles.infrastructure.mappers.RoleMapper;
import com.easy.store.backend.context.user.application.dto.UserDTO;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class UserMapper extends BaseMapper<UserEntity, User, UserDTO> {

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
                .updateDate(entity.getUpdateDate())
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
                .updateDate(model.getUpdateDate())
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
                .role(roleMapper.dtoToModel(dto.getRole()))
                .build();
    }

}
