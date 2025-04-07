package com.easy.store.backend.context.category_has_payment_type.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CategoryHasPaymentTypeId {

    private Long categoryId;
    private Long paymentTypeId;

    @Override
    public String toString() {
        return "CategoryHasPaymentTypeId{" +
                "categoryId=" + categoryId +
                ", paymentTypeId=" + paymentTypeId +
                '}';
    }
}
