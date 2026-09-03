package com.easy.store.backend.security.models;

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
public class ResetPasswordRequest {

    @NotBlank(message = "el username es obligatorio")
    String username;

    @NotNull(message = "el codigo es obligatorio")
    Long code;

    // @ToString.Exclude evita que la contraseña quede expuesta en texto
    // claro si en algun momento se logea el objeto completo (lombok @Data
    // genera toString() con todos los campos por defecto).
    @ToString.Exclude
    @NotBlank(message = "la contraseña es obligatoria")
    String password;

    @ToString.Exclude
    @NotBlank(message = "la confirmación de contraseña es obligatoria")
    String confirmPassword;

    public boolean isValid() {
        if(username == null || code == null ||
                password == null || confirmPassword == null) return false;
        return !username.isEmpty() &&
                !password.isEmpty() && !confirmPassword.isEmpty() &&
                password.equals(confirmPassword);
    }

}
