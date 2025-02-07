package com.easy.store.backend.context.purchase_has_product.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PurchaseHasProductId {

    private Long purchaseId;
    private Long productId;

    public boolean isValid(PurchaseHasProductId accountHasUserId) {
        return accountHasUserId.getPurchaseId() != null && accountHasUserId.getProductId() != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseHasProductId that)) return false;
        return Objects.equals(getPurchaseId(), that.getPurchaseId()) && Objects.equals(getProductId(), that.getProductId());
    }

    @Override
    public String toString() {
        return "PurchaseHasProductId{" +
                "purchaseId=" + purchaseId +
                ", productId=" + productId +
                '}';
    }
}
