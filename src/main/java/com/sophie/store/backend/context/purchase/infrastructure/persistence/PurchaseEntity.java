package com.sophie.store.backend.context.purchase.infrastructure.persistence;

import com.sophie.store.backend.context.payment_type.domain.model.PaymentType;
import com.sophie.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.sophie.store.backend.context.user.domain.model.User;
import com.sophie.store.backend.context.user.infrastructure.persistence.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase")
public class PurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "payment_type_id")
    private PaymentTypeEntity paymentType;

    @CreationTimestamp
    @Column(name = "date", updatable = false)
    private Timestamp date;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "create_by", updatable = false)
    private Long createBy;

    @PrePersist
    private void onCreate() { total = new BigDecimal(0); }

}
