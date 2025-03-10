package com.easy.store.backend.context.product.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDTO {
    private Long id;
    private String name;
    private String description;
    private String imageName;
    private BigDecimal price;
    private Integer quantity;
    private Integer qualification;
    private Long updateBy;
    private Long subcategoryId;
}
