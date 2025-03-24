package com.easy.store.backend.context.codes.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "codes")
public class CodeEntity {

    @Id
    private Long userId;

    @Column(name = "code")
    private Long code;

    @Column(name = "action")
    private String action;

    @Column(name = "creation_date")
    private Timestamp creationDate;

}
