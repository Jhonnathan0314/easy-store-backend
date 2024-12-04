package com.easy.store.backend.context.roles.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RoleResponseDTO {
    private Long id;
    private String name;
}
