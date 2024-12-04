package com.easy.store.backend.context.account_has_user.infrastructure.persistence;

import com.easy.store.backend.context.account.infrastructure.persistence.AccountEntity;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account_has_user")
public class AccountHasUserEntity {

    @EmbeddedId
    private AccountHasUserId id;

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id", updatable = false)
    private AccountEntity accountId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", updatable = false)
    private UserEntity userId;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private Timestamp creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private Timestamp updateDate;

    @Column(name = "state")
    private String state;

    @PrePersist
    protected void onCreate() {
        this.state = "active";
    }

}
