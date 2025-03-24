package com.easy.store.backend.security.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {

    String username;
    Long code;
    String password;
    String confirmPassword;

    public boolean isValid() {
        if(username == null || code == null ||
                password == null || confirmPassword == null) return false;
        return !username.isEmpty() &&
                !password.isEmpty() && !confirmPassword.isEmpty() &&
                password.equals(confirmPassword);
    }

}
