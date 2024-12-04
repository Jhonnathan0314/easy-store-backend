package com.easy.store.backend.context.user.infrastructure.persistence;

import com.easy.store.backend.context.roles.infrastructure.persistence.RoleEntity;
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
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private Timestamp creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private Timestamp updateDate;

    @Column(name = "state")
    private String state;

    @ManyToOne
    @JoinColumn(name = "role_id", updatable = false)
    private RoleEntity role;

    @PrePersist
    protected void onCreate() {
        this.state = "active";
        this.role = RoleEntity.builder()
                .id(1L)
                .name("client")
                .build();
    }

}
