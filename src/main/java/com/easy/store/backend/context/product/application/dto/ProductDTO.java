package com.easy.store.backend.context.product.application.dto;

import com.easy.store.backend.context.subcategory.application.dto.SubcategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Integer qualification;
    private SubcategoryDTO subcategory;
    private Long createBy;
    private Long updateBy;
}
