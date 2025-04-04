package com.easy.store.backend.context.product.infrastructure.mappers;

import com.easy.store.backend.context.product.application.dto.ProductDTO;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.infrastructure.persistence.ProductEntity;
import com.easy.store.backend.context.subcategory.infrastructure.mappers.SubcategoryMapper;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class ProductMapper extends BaseMapper<ProductEntity, Product, ProductDTO> {

    private final SubcategoryMapper subcategoryMapper = new SubcategoryMapper();

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
                .subcategory(subcategoryMapper.entityToModel(entity.getSubcategory()))
                .categoryId(entity.getSubcategory().getCategory().getId())
                .createBy(entity.getCreateBy())
                .updateBy(entity.getUpdateBy())
                .creationDate(entity.getCreationDate())
                .updateDate(entity.getUpdateDate())
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
                .subcategory(subcategoryMapper.modelToEntity(model.getSubcategory()))
                .createBy(model.getCreateBy())
                .updateBy(model.getUpdateBy())
                .creationDate(model.getCreationDate())
                .updateDate(model.getUpdateDate())
                .state(model.getState())
                .build();
    }

    @Override
    public ProductDTO modelToDto(Product model) {
        return ProductDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .imageName(model.getImageName())
                .imageNumber(model.getImageNumber())
                .imageLastNumber(model.getImageLastNumber())
                .price(model.getPrice())
                .quantity(model.getQuantity())
                .qualification(model.getQualification())
                .subcategory(subcategoryMapper.modelToDto(model.getSubcategory()))
                .categoryId(model.getCategoryId())
                .createBy(model.getCreateBy())
                .updateBy(model.getUpdateBy())
                .build();
    }

    @Override
    public Product dtoToModel(ProductDTO dto) {
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
                .subcategory(subcategoryMapper.dtoToModel(dto.getSubcategory()))
                .categoryId(dto.getCategoryId())
                .createBy(dto.getCreateBy())
                .updateBy(dto.getUpdateBy())
                .build();
    }

}