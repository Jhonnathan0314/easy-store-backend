package com.easy.store.backend.context.category_has_payment_type.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "el categoryId es obligatorio")
    private Long categoryId;

    @NotNull(message = "el paymentTypeId es obligatorio")
    private Long paymentTypeId;

    @Override
    public String toString() {
        return "CategoryHasPaymentTypeId{" +
                "categoryId=" + categoryId +
                ", paymentTypeId=" + paymentTypeId +
                '}';
    }
}
