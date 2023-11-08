package com.sophie.store.backend.context.user.application.dto;

import com.sophie.store.backend.context.roles.application.dto.RoleDTO;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String name;
    private String lastName;
    private String password;
    private RoleDTO role;
}
