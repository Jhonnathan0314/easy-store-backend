package com.easy.store.backend.context.purchase_has_product.infrastructure.persistence;

import com.easy.store.backend.context.product.infrastructure.persistence.ProductEntity;
import com.easy.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase_has_product")
public class PurchaseHasProductEntity {

    @EmbeddedId
    private PurchaseHasProductId id;

    @ManyToOne
    @MapsId("purchaseId")
    @JoinColumn(name = "purchase_id", updatable = false)
    private PurchaseEntity purchase;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", updatable = false)
    private ProductEntity product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Override
    public String toString() {
        return "PurchaseHasProductEntity{" +
                "id=" + id +
                ", purchase=" + purchase +
                ", product=" + product +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", subtotal=" + subtotal +
                '}';
    }
}
