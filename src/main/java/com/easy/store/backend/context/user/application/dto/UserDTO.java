package com.easy.store.backend.context.user.application.dto;

import com.easy.store.backend.context.roles.application.dto.RoleDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "el username es obligatorio")
    private String username;

    private String name;
    private String lastName;
    private String password;
    private RoleDTO role;
}
