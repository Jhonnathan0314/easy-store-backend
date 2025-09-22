package com.easy.store.backend.context.product.domain.model;

import com.easy.store.backend.context.s3.model.S3File;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private String description;
    private String imageName;
    private List<S3File> images;
    private Integer imageNumber;
    private Integer imageLastNumber;
    private BigDecimal price;
    private Integer quantity;
    private Integer qualification;
    private Subcategory subcategory;
    private Long categoryId;
    private Long createBy;
    private Long updateBy;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private String state;

    public boolean isValid() {
        if(name == null ||
                subcategory == null ||
                price == null ||
                quantity == null ||
                description == null ||
                qualification == null
        ) return false;

        if (name.isEmpty() || description.isEmpty()) return false;
        if (price.compareTo(BigDecimal.ZERO) <= 0) return false;
        if (quantity < 0 || qualification < 0) return false;

        return subcategory.getId() != null;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageName='" + imageName + '\'' +
                ", imageNumber=" + imageNumber +
                ", imageLastNumber=" + imageLastNumber +
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
