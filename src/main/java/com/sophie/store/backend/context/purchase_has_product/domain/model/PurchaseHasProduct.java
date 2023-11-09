package com.sophie.store.backend.context.purchase_has_product.domain.model;

import com.sophie.store.backend.context.product.domain.model.Product;
import com.sophie.store.backend.context.purchase.domain.model.Purchase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class PurchaseHasProduct {
    private Long id;
    private Purchase purchase;
    private Product product;
    private Integer quantity;

    public boolean isValid(PurchaseHasProduct purchaseHasProduct) {
        if(purchaseHasProduct.getQuantity() == null) return false;

        return !purchaseHasProduct.getQuantity().toString().isEmpty();
    }
}
