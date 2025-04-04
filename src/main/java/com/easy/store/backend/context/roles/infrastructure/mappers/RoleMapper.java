package com.easy.store.backend.context.roles.infrastructure.mappers;

import com.easy.store.backend.context.roles.application.dto.RoleDTO;
import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.infrastructure.persistence.RoleEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class RoleMapper extends BaseMapper<RoleEntity, Role, RoleDTO> {

    @Override
    public Role entityToModel(RoleEntity entity) {
        return Role.builder()
                .id(entity.getId())
                .name(entity.getName())
                .creationDate(entity.getCreationDate())
                .updateDate(entity.getUpdateDate())
                .state(entity.getState())
                .build();
    }

    @Override
    public RoleEntity modelToEntity(Role model) {
        return RoleEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .creationDate(model.getCreationDate())
                .updateDate(model.getUpdateDate())
                .state(model.getState())
                .build();
    }

    @Override
    public RoleDTO modelToDto(Role model) {
        return RoleDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
    }

    @Override
    public Role dtoToModel(RoleDTO dto) {
        return Role.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

}
