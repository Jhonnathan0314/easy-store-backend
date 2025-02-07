package com.easy.store.backend.context.purchase.infrastructure.persistence;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
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

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "state")
    private String state;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private Timestamp creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private Timestamp updateDate;

    @Column(name = "create_by", updatable = false)
    private Long createBy;

    @Column(name = "update_by")
    private Long updateBy;

    @PrePersist
    protected void onCreate() { total = new BigDecimal(0); }

    @Override
    public String toString() {
        return "PurchaseEntity{" +
                "id=" + id +
                ", user=" + user +
                ", paymentType=" + paymentType +
                ", total=" + total +
                ", state='" + state + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", createBy=" + createBy +
                ", updateBy=" + updateBy +
                '}';
    }
}
