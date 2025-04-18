package com.easy.store.backend.context.user.application.dto;

import com.easy.store.backend.context.roles.application.dto.RoleResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {
    private String username;
    private String name;
    private String lastName;
    private String password;
    private RoleResponseDTO role;
}
