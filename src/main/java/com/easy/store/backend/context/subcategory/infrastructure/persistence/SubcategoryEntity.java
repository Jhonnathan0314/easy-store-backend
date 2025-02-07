package com.easy.store.backend.context.subcategory.infrastructure.persistence;

import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
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
@Table(name = "subcategory")
public class SubcategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(name = "create_by", updatable = false)
    private Long createBy;

    @Column(name = "update_by")
    private Long updateBy;

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
        return "SubcategoryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", createBy=" + createBy +
                ", updateBy=" + updateBy +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", state='" + state + '\'' +
                '}';
    }
}
