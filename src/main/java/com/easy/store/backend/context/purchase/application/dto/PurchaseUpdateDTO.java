package com.easy.store.backend.context.purchase.application.dto;

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
public class PurchaseUpdateDTO {

    @NotNull(message = "el id es obligatorio")
    private Long id;

    @NotNull(message = "el paymentTypeId es obligatorio")
    private Long paymentTypeId;

    @NotBlank(message = "el estado es obligatorio")
    private String state;

    private Long updateBy;
}
