package com.easy.store.backend.context.purchase_has_product.application.dto;

import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseHasProductAddDTO {

    @NotNull(message = "el id es obligatorio")
    @Valid
    private PurchaseHasProductId id;

    @NotNull(message = "la cantidad es obligatoria")
    @Min(value = 0, message = "la cantidad no puede ser negativa")
    private Integer quantity;
}
