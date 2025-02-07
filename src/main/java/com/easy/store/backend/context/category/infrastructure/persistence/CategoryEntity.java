package com.easy.store.backend.context.category.infrastructure.persistence;

import com.easy.store.backend.context.account.infrastructure.persistence.AccountEntity;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_name")
    private String imageName;
    
    @Column(name = "create_by", updatable = false)
    private Long createBy;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private Timestamp creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private Timestamp updateDate;

    @Column(name = "state")
    private String state;

    @PrePersist
    protected void onCreate() { this.state = "active"; }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageName='" + imageName + '\'' +
                ", createBy=" + createBy +
                ", updateBy=" + updateBy +
                ", user=" + user +
                ", account=" + account +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", state='" + state + '\'' +
                '}';
    }
}
