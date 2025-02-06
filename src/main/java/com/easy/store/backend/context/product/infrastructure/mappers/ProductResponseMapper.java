package com.easy.store.backend.context.product.infrastructure.mappers;

import com.easy.store.backend.context.product.application.dto.ProductResponseDTO;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.infrastructure.persistence.ProductEntity;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.infrastructure.persistence.SubcategoryEntity;
import com.easy.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponseMapper implements Mapper<ProductEntity, Product, ProductResponseDTO> {

    @Override
    public Product entityToModel(ProductEntity entity) {
        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .qualification(entity.getQualification())
                .subcategory(Subcategory.builder()
                        .id(entity.getSubcategory().getId())
                        .build()
                )
                .build();
    }

    @Override
    public ProductEntity modelToEntity(Product model) {
        return ProductEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .price(model.getPrice())
                .quantity(model.getQuantity())
                .qualification(model.getQualification())
                .subcategory(SubcategoryEntity.builder()
                        .id(model.getSubcategory().getId())
                        .build()
                )
                .build();
    }

    @Override
    public ProductResponseDTO modelToDto(Product model) {
        return ProductResponseDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .price(model.getPrice())
                .quantity(model.getQuantity())
                .qualification(model.getQualification())
                .subcategoryId(model.getSubcategory().getId())
                .build();
    }

    @Override
    public Product dtoToModel(ProductResponseDTO dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .qualification(dto.getQualification())
                .subcategory(Subcategory.builder()
                        .id(dto.getSubcategoryId())
                        .build()
                )
                .build();
    }

    @Override
    public List<Product> entitiesToModels(List<ProductEntity> entities) {
        return entities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductEntity> modelsToEntities(List<Product> models) {
        return models.stream()
                .map(this::modelToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> modelsToDtos(List<Product> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> dtosToModels(List<ProductResponseDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}