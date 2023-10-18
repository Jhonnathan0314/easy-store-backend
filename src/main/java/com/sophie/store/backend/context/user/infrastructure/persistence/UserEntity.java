package com.sophie.store.backend.context.user.infrastructure.persistence;

import com.sophie.store.backend.context.roles.infrastructure.persistence.RoleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
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

    @Column(name = "creation_date", insertable = false)
    private Timestamp creationDate;

    @Column(name = "state", insertable = false)
    private String state;

    @OneToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

}
