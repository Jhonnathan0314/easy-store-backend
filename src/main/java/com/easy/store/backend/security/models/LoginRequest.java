package com.easy.store.backend.security.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "el username es obligatorio")
    String username;

    // Sin @NotBlank a proposito: el flujo de login del usuario ghost envia
    // password="" desde el frontend, y AuthorizationService.login reemplaza
    // ese valor por la contraseña real ANTES de la validacion de negocio
    // (isValidRequest). Si se exige aqui que no este en blanco, el login del
    // ghost se rechazaria con 400 antes de llegar al servicio.
    String password;

    public boolean isValidRequest(LoginRequest request) {
        if(request.getUsername() == null || request.getPassword() == null) return false;
        return !request.getUsername().isEmpty()  && !request.getPassword().isEmpty();
    }
}
