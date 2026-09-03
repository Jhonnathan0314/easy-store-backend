package com.easy.store.backend.context.codes.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Code {

    @NotNull(message = "el userId es obligatorio")
    private Long userId;

    @NotNull(message = "el codigo es obligatorio")
    private Long code;

    @NotBlank(message = "la accion es obligatoria")
    private String action;

    private Timestamp creationDate;

    public boolean isValid() {
        if(userId == null && code == null && action == null) return false;
        return !action.isEmpty();
    }

}
