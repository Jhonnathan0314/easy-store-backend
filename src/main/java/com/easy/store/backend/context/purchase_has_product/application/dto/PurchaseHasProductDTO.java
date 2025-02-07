package com.easy.store.backend.context.purchase_has_product.application.dto;

import com.easy.store.backend.context.product.application.dto.ProductDTO;
import com.easy.store.backend.context.purchase.application.dto.PurchaseDTO;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class PurchaseHasProductDTO {
    private PurchaseHasProductId id;
    private PurchaseDTO purchase;
    private ProductDTO product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}
