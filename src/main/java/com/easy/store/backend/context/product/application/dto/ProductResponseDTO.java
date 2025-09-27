package com.easy.store.backend.context.product.application.dto;

import com.easy.store.backend.context.s3.model.S3File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String imageName;
    private List<S3File> images;
    private Integer imageNumber;
    private Integer imageLastNumber;
    private BigDecimal price;
    private Integer quantity;
    private Integer qualification;
    private Long subcategoryId;
    private Long categoryId;
}
