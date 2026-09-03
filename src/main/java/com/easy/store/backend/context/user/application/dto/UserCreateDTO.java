package com.easy.store.backend.context.user.application.dto;

import com.easy.store.backend.context.roles.application.dto.RoleResponseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    @NotBlank(message = "el username es obligatorio")
    private String username;

    @NotBlank(message = "el nombre es obligatorio")
    private String name;

    @NotBlank(message = "el apellido es obligatorio")
    private String lastName;

    @NotBlank(message = "la contraseña es obligatoria")
    private String password;

    private RoleResponseDTO role;
}
