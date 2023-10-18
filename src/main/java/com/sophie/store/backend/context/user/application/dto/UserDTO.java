package com.sophie.store.backend.context.user.application.dto;

import com.sophie.store.backend.context.roles.application.dto.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String name;
    private String lastName;
    private String password;
    private Timestamp creationDate;
    private String state;
    private RoleDTO role;
}
