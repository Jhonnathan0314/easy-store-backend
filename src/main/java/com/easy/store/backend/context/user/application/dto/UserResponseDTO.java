package com.easy.store.backend.context.user.application.dto;

import com.easy.store.backend.context.roles.application.dto.RoleResponseDTO;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String username;
    private String name;
    private String lastName;
    private RoleResponseDTO role;
}
