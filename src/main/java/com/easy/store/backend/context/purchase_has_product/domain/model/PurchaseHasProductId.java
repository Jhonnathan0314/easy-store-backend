package com.easy.store.backend.context.purchase_has_product.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "el purchaseId es obligatorio")
    private Long purchaseId;

    @NotNull(message = "el productId es obligatorio")
    private Long productId;

    public boolean isValid() {
        return purchaseId != null && productId != null;
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
