package com.easy.store.backend.context.category_has_payment_type.infrastructure.persistence;


import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentTypeId;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category_has_payment_type")
public class CategoryHasPaymentTypeEntity {

    @EmbeddedId
    private CategoryHasPaymentTypeId id;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", updatable = false)
    private CategoryEntity category;

    @ManyToOne
    @MapsId("paymentTypeId")
    @JoinColumn(name = "payment_type_id", updatable = false)
    private PaymentTypeEntity paymentType;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "email")
    private String email;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "account_bank")
    private String accountBank;

    @Column(name = "state")
    private String state;

}
