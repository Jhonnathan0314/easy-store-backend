package com.easy.store.backend.context.store_request.infrastructure.persistence;

import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "store_request")
public class StoreRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "store_description")
    private String storeDescription;

    @Column(name = "status")
    private String status;

    @Column(name = "reviewed_by")
    private Long reviewedBy;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private Timestamp creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private Timestamp updateDate;

    @Override
    public String toString() {
        return "StoreRequestEntity{" +
                "id=" + id +
                ", user=" + user +
                ", storeName='" + storeName + '\'' +
                ", storeDescription='" + storeDescription + '\'' +
                ", status='" + status + '\'' +
                ", reviewedBy=" + reviewedBy +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
