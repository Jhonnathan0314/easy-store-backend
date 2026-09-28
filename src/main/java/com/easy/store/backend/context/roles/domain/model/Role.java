package com.easy.store.backend.context.roles.domain.model;

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

    // Antes esta condicion estaba invertida (return name.isEmpty()), lo que hacia que un nombre
    // vacio se considerara valido (isValid()==true) y un nombre con contenido se rechazara
    // (isValid()==false). Esto afectaba tanto CreateRoleUseCase como UpdateRoleUseCase.
    public boolean isValid() {
        if(name == null) return false;
        return !name.isEmpty();
    }
}
