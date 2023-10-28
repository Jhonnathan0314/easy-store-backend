package com.sophie.store.backend.context.product.infrastructure.mappers;

import com.sophie.store.backend.context.product.application.dto.ProductDTO;
import com.sophie.store.backend.context.product.domain.model.Product;
import com.sophie.store.backend.context.product.infrastructure.persistence.ProductEntity;
import com.sophie.store.backend.context.subcategory.infrastructure.mappers.SubcategoryMapper;
import com.sophie.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper implements Mapper<ProductEntity, Product, ProductDTO> {

    private final SubcategoryMapper subcategoryMapper = new SubcategoryMapper();

    @Override
    public Product entityToModel(ProductEntity entity) {
        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .qualification(entity.getQualification())
                .subcategory(subcategoryMapper.entityToModel(entity.getSubcategory()))
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
                .price(model.getPrice())
                .quantity(model.getQuantity())
                .qualification(model.getQualification())
                .subcategory(subcategoryMapper.modelToDto(model.getSubcategory()))
                .createBy(model.getCreateBy())
                .updateBy(model.getUpdateBy())
                .creationDate(model.getCreationDate())
                .updateDate(model.getUpdateDate())
                .state(model.getState())
                .build();
    }

    @Override
    public Product dtoToModel(ProductDTO dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .qualification(dto.getQualification())
                .subcategory(subcategoryMapper.dtoToModel(dto.getSubcategory()))
                .createBy(dto.getCreateBy())
                .updateBy(dto.getUpdateBy())
                .creationDate(dto.getCreationDate())
                .updateDate(dto.getUpdateDate())
                .state(dto.getState())
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
    public List<ProductDTO> modelsToDtos(List<Product> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> dtosToModels(List<ProductDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

}