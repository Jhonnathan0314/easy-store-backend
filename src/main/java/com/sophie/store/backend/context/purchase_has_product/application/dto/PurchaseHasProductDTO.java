package com.sophie.store.backend.context.purchase_has_product.application.dto;

import com.sophie.store.backend.context.product.application.dto.ProductDTO;
import com.sophie.store.backend.context.purchase.application.dto.PurchaseDTO;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class PurchaseHasProductDTO {
    private Long id;
    private PurchaseDTO purchase;
    private ProductDTO product;
    private Integer quantity;
}
