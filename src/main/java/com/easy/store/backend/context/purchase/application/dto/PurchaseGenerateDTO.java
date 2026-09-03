package com.easy.store.backend.context.purchase.application.dto;

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
public class PurchaseGenerateDTO {

    @NotNull(message = "el userId es obligatorio")
    private Long userId;

    @NotNull(message = "el paymentTypeId es obligatorio")
    private Long paymentTypeId;

    @NotNull(message = "el categoryId es obligatorio")
    private Long categoryId;

    private BigDecimal total;

    @NotBlank(message = "el estado es obligatorio")
    private String state;

    private Long createBy;
}
