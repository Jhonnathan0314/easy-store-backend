package com.easy.store.backend.context.category.application.dto;

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
public class CategoryCreateDTO {

    @NotBlank(message = "el nombre es obligatorio")
    private String name;

    @NotBlank(message = "la descripción es obligatoria")
    private String description;

    private Long createBy;

    @NotNull(message = "el userId es obligatorio")
    private Long userId;

    @NotNull(message = "el accountId es obligatorio")
    private Long accountId;
}
