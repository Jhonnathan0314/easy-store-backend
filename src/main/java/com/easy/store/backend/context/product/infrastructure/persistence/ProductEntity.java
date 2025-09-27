package com.easy.store.backend.context.product.infrastructure.persistence;

import com.easy.store.backend.context.subcategory.infrastructure.persistence.SubcategoryEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_number")
    private Integer imageNumber;

    @Column(name = "image_last_number")
    private Integer imageLastNumber;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "qualification")
    private Integer qualification;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private SubcategoryEntity subcategory;

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
        return "ProductEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageName='" + imageName + '\'' +
                ", imageNumber='" + imageNumber + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", qualification=" + qualification +
                ", subcategory=" + subcategory +
                ", createBy=" + createBy +
                ", updateBy=" + updateBy +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", state='" + state + '\'' +
                '}';
    }
}
