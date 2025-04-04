package com.easy.store.backend.context.purchase_has_product.domain.model;

import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class PurchaseHasProduct {

    private PurchaseHasProductId id;
    private Purchase purchase;
    private Product product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

    public boolean isValid() {
        if(quantity == null ||
            id == null) return false;

        return !quantity.toString().isEmpty();
    }

    @Override
    public String toString() {
        return "PurchaseHasProduct{" +
                "id=" + id +
                ", purchase=" + purchase +
                ", product=" + product +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", subtotal=" + subtotal +
                '}';
    }
}
