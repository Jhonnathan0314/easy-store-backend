package com.easy.store.backend.context.product.infrastructure.mappers;

import com.easy.store.backend.context.product.application.dto.ProductResponseDTO;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.infrastructure.persistence.ProductEntity;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.infrastructure.persistence.SubcategoryEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class ProductResponseMapper extends BaseMapper<ProductEntity, Product, ProductResponseDTO> {

    @Override
    public Product entityToModel(ProductEntity entity) {
        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .imageName(entity.getImageName())
                .imageNumber(entity.getImageNumber())
                .imageLastNumber(entity.getImageLastNumber())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .qualification(entity.getQualification())
                .subcategory(Subcategory.builder()
                        .id(entity.getSubcategory().getId())
                        .build()
                )
                .categoryId(entity.getSubcategory().getCategory().getId())
                .state(entity.getState())
                .build();
    }

    @Override
    public ProductEntity modelToEntity(Product model) {
        return ProductEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .imageName(model.getImageName())
                .imageNumber(model.getImageNumber())
                .imageLastNumber(model.getImageLastNumber())
                .price(model.getPrice())
                .quantity(model.getQuantity())
                .qualification(model.getQualification())
                .subcategory(SubcategoryEntity.builder()
                        .id(model.getSubcategory().getId())
                        .build()
                )
                .state(model.getState())
                .build();
    }

    @Override
    public ProductResponseDTO modelToDto(Product model) {
        return ProductResponseDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .imageName(model.getImageName())
                .imageNumber(model.getImageNumber())
                .imageLastNumber(model.getImageLastNumber())
                .price(model.getPrice())
                .quantity(model.getQuantity())
                .qualification(model.getQualification())
                .subcategoryId(model.getSubcategory().getId())
                .categoryId(model.getCategoryId())
                .build();
    }

    @Override
    public Product dtoToModel(ProductResponseDTO dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .imageName(dto.getImageName())
                .imageNumber(dto.getImageNumber())
                .imageLastNumber(dto.getImageLastNumber())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .qualification(dto.getQualification())
                .subcategory(Subcategory.builder()
                        .id(dto.getSubcategoryId())
                        .build()
                )
                .categoryId(dto.getCategoryId())
                .build();
    }

}