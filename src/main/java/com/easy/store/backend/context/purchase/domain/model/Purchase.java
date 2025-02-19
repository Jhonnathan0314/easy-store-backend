package com.easy.store.backend.context.purchase.domain.model;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Purchase {
    private Long id;
    private User user;
    private PaymentType paymentType;
    private Category category;
    private BigDecimal total;
    private String state;
    private Long createBy;
    private Long updateBy;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private List<PurchaseHasProduct> products;

    public boolean isValid(Purchase purchase) {
        if(purchase.getUser() == null ||
                purchase.getPaymentType() == null ||
                purchase.getCategory() == null ||
                purchase.state == null) return false;

        return purchase.getUser().getId() != null &&
                purchase.getPaymentType().getId() != null &&
                purchase.getCategory().getId() != null &&
                !purchase.getState().isEmpty();
    }
}
