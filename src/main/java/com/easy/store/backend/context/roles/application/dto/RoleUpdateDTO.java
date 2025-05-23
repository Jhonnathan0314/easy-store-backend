package com.easy.store.backend.context.roles.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleUpdateDTO {
    private Long id;
    private String name;
    private String state;
}
