package com.sophie.store.backend.context.user.application.dto;

import com.sophie.store.backend.context.roles.application.dto.RoleResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String username;
    private String name;
    private String lastName;
    private RoleResponseDTO role;
}
