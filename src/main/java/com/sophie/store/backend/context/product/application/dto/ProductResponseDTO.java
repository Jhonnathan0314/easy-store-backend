package com.sophie.store.backend.context.product.application.dto;

import com.sophie.store.backend.context.subcategory.application.dto.SubcategoryResponseDTO;
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
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Integer qualification;
    private SubcategoryResponseDTO subcategory;
}
