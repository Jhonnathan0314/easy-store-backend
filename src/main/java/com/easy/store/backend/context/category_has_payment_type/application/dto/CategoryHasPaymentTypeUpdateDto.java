package com.easy.store.backend.context.category_has_payment_type.application.dto;

import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentTypeId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryHasPaymentTypeUpdateDto {

    @NotNull(message = "el id es obligatorio")
    @Valid
    private CategoryHasPaymentTypeId id;

    private Long phone;
    private String email;
    private String accountNumber;
    private String accountType;
    private String accountBank;

}
