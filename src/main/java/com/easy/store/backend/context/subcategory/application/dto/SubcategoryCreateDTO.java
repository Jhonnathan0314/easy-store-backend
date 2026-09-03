package com.easy.store.backend.context.subcategory.application.dto;

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
public class SubcategoryCreateDTO {

    @NotBlank(message = "el nombre es obligatorio")
    private String name;

    @NotNull(message = "el categoryId es obligatorio")
    private Long categoryId;

    private Long createBy;
}
