package com.easy.store.backend.context.payment_type.application.dto;

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
public class PaymentTypeUpdateDTO {

    @NotNull(message = "el id es obligatorio")
    private Long id;

    @NotBlank(message = "el nombre es obligatorio")
    private String name;

    private Long updateBy;
}
