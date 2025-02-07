package com.easy.store.backend.context.purchase_has_product.application.dto;

import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseHasProductUpdateDTO {
    private PurchaseHasProductId id;
    private Integer quantity;
}
