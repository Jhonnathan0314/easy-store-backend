package com.easy.store.backend.context.product.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDTO {
    private String code;

    @NotBlank(message = "el nombre es obligatorio")
    private String name;

    @NotBlank(message = "la descripción es obligatoria")
    private String description;

    private String imageName;
    private Integer imageNumber;
    private Integer imageLastNumber;

    @NotNull(message = "el precio es obligatorio")
    @DecimalMin(value = "0.01", message = "el precio debe ser mayor a 0")
    private BigDecimal price;

    @NotNull(message = "la cantidad es obligatoria")
    @Min(value = 0, message = "la cantidad no puede ser negativa")
    private Integer quantity;

    @NotNull(message = "la calificación es obligatoria")
    @Min(value = 0, message = "la calificación no puede ser negativa")
    private Integer qualification;

    private Long createBy;

    @NotNull(message = "el subcategoryId es obligatorio")
    private Long subcategoryId;
}
