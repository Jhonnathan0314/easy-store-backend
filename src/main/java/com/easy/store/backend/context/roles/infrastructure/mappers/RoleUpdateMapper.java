package com.easy.store.backend.context.roles.infrastructure.mappers;

import com.easy.store.backend.context.roles.application.dto.RoleUpdateDTO;
import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.infrastructure.persistence.RoleEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class RoleUpdateMapper extends BaseMapper<RoleEntity, Role, RoleUpdateDTO> {

    @Override
    public Role entityToModel(RoleEntity entity) {
        return Role.builder()
                .id(entity.getId())
                .name(entity.getName())
                .state(entity.getState())
                .build();
    }

    @Override
    public RoleEntity modelToEntity(Role model) {
        return RoleEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .state(model.getState())
                .build();
    }

    @Override
    public RoleUpdateDTO modelToDto(Role model) {
        return RoleUpdateDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .state(model.getState())
                .build();
    }

    @Override
    public Role dtoToModel(RoleUpdateDTO dto) {
        return Role.builder()
                .id(dto.getId())
                .name(dto.getName())
                .state(dto.getState())
                .build();
    }

}
