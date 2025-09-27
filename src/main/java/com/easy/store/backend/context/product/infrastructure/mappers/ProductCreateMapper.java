package com.easy.store.backend.context.product.infrastructure.mappers;

import com.easy.store.backend.context.product.application.dto.ProductCreateDTO;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.infrastructure.persistence.ProductEntity;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.infrastructure.persistence.SubcategoryEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class ProductCreateMapper extends BaseMapper<ProductEntity, Product, ProductCreateDTO> {

    @Override
    public Product entityToModel(ProductEntity entity) {
        return Product.builder()
                .code(entity.getCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .imageName(entity.getImageName())
                .imageNumber(entity.getImageNumber())
                .imageLastNumber(entity.getImageLastNumber())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .qualification(entity.getQualification())
                .createBy(entity.getCreateBy())
                .subcategory(Subcategory.builder()
                        .id(entity.getSubcategory().getId())
                        .build()
                )
                .build();
    }

    @Override
    public ProductEntity modelToEntity(Product model) {
        return ProductEntity.builder()
                .code(model.getCode())
                .name(model.getName())
                .description(model.getDescription())
                .imageName(model.getImageName())
                .imageNumber(model.getImageNumber())
                .imageLastNumber(model.getImageLastNumber())
                .price(model.getPrice())
                .quantity(model.getQuantity())
                .qualification(model.getQualification())
                .createBy(model.getCreateBy())
                .subcategory(SubcategoryEntity.builder()
                        .id(model.getSubcategory().getId())
                        .build()
                )
                .build();
    }

    @Override
    public ProductCreateDTO modelToDto(Product model) {
        return ProductCreateDTO.builder()
                .code(model.getCode())
                .name(model.getName())
                .description(model.getDescription())
                .imageName(model.getImageName())
                .imageNumber(model.getImageNumber())
                .imageLastNumber(model.getImageLastNumber())
                .price(model.getPrice())
                .quantity(model.getQuantity())
                .qualification(model.getQualification())
                .createBy(model.getCreateBy())
                .subcategoryId(model.getSubcategory().getId())
                .build();
    }

    @Override
    public Product dtoToModel(ProductCreateDTO dto) {
        return Product.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .description(dto.getDescription())
                .imageName(dto.getImageName())
                .imageNumber(dto.getImageNumber())
                .imageLastNumber(dto.getImageLastNumber())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .qualification(dto.getQualification())
                .createBy(dto.getCreateBy())
                .subcategory(Subcategory.builder()
                        .id(dto.getSubcategoryId())
                        .build()
                )
                .build();
    }

}