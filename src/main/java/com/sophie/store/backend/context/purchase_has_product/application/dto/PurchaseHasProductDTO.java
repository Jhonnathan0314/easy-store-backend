package com.sophie.store.backend.context.purchase_has_product.application.dto;

import com.sophie.store.backend.context.product.application.dto.ProductDTO;
import com.sophie.store.backend.context.purchase.application.dto.PurchaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseHasProductDTO {
    private Long id;
    private PurchaseDTO purchase;
    private ProductDTO product;
    private Integer quantity;
}
