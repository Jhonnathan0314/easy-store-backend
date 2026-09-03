package com.easy.store.backend.context.user.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {

    @NotNull(message = "el id es obligatorio")
    private Long id;

    @NotBlank(message = "el username es obligatorio")
    private String username;

    @NotBlank(message = "el nombre es obligatorio")
    private String name;

    @NotBlank(message = "el apellido es obligatorio")
    private String lastName;

    // Sin @NotBlank a proposito: UpdateUserUseCase permite password=null para
    // no cambiar la contraseña actual del usuario.
    // @ToString.Exclude evita que la contraseña quede expuesta en texto
    // claro si en algun momento se logea el objeto completo (lombok @Data
    // genera toString() con todos los campos por defecto).
    @ToString.Exclude
    private String password;
}
