package com.easy.store.backend.context.purchase_has_product.application.dto;

import com.easy.store.backend.context.product.application.dto.ProductResponseDTO;
import com.easy.store.backend.context.purchase.application.dto.PurchaseResponseDTO;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class PurchaseHasProductResponseDTO {
    private Long id;
    private PurchaseResponseDTO purchase;
    private ProductResponseDTO product;
    private Integer quantity;
}
