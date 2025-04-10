package com.easy.store.backend.context.purchase_has_product.application.dto;

import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class PurchaseHasProductResponseDTO {
    private PurchaseHasProductId id;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}
