package com.sophie.store.backend.context.product.domain.model;

import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.subcategory.domain.model.Subcategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Integer qualification;
    private Subcategory subcategory;
    private Long createBy;
    private Long updateBy;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private String state;

    public boolean isValid(Product product) {
        if(product.getName() == null ||
                product.getSubcategory() == null ||
                product.getPrice() == null ||
                product.getQuantity() == null ||
                product.getDescription() == null ||
                product.getQualification() == null
        ) return false;

        if (product.getName().isEmpty() || product.getDescription().isEmpty()) return false;
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) return false;
        if (product.getQuantity() < 0 || product.getQualification() < 0) return false;

        return product.getSubcategory().getId() != null;
    }
}
