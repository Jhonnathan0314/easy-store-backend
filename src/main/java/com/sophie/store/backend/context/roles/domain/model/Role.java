package com.sophie.store.backend.context.roles.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    private Long id;
    private String name;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private String state;

    public boolean isValid(Role role) {
        if(role.getName() == null) return false;
        return !role.getName().isEmpty();
    }
}
