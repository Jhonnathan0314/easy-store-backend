package com.easy.store.backend.context.account.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateDto {

    @NotNull(message = "el id es obligatorio")
    private Long id;

    @NotBlank(message = "el nombre es obligatorio")
    private String name;

    @NotBlank(message = "la descripción es obligatoria")
    private String description;

    private String state;
    private String imageName;

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", state=" + state +
                '}';
    }
}
