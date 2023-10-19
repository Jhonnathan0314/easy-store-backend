package com.sophie.store.backend.security.models;

import com.sophie.store.backend.context.roles.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    String username;
    String password;
    String name;
    String lastName;
    Role role;
}
