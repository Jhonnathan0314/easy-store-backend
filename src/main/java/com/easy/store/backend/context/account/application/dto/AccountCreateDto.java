package com.easy.store.backend.context.account.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreateDto {

    @NotBlank(message = "el nombre es obligatorio")
    private String name;

    @NotBlank(message = "la descripción es obligatoria")
    private String description;

    private String imageName;

    @Override
    public String toString() {
        return "AccountDto{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
