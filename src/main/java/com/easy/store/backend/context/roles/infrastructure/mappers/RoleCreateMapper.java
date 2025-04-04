package com.easy.store.backend.context.roles.infrastructure.mappers;

import com.easy.store.backend.context.roles.application.dto.RoleCreateDTO;
import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.infrastructure.persistence.RoleEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class RoleCreateMapper extends BaseMapper<RoleEntity, Role, RoleCreateDTO> {

    @Override
    public Role entityToModel(RoleEntity entity) {
        return Role.builder()
                .name(entity.getName())
                .build();
    }

    @Override
    public RoleEntity modelToEntity(Role model) {
        return RoleEntity.builder()
                .name(model.getName())
                .build();
    }

    @Override
    public RoleCreateDTO modelToDto(Role model) {
        return RoleCreateDTO.builder()
                .name(model.getName())
                .build();
    }

    @Override
    public Role dtoToModel(RoleCreateDTO dto) {
        return Role.builder()
                .name(dto.getName())
                .build();
    }

}
