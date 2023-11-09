package com.sophie.store.backend.context.product.data;

import com.sophie.store.backend.context.category.application.dto.CategoryDTO;
import com.sophie.store.backend.context.category.application.dto.CategoryResponseDTO;
import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.sophie.store.backend.context.product.application.dto.ProductCreateDTO;
import com.sophie.store.backend.context.product.application.dto.ProductDTO;
import com.sophie.store.backend.context.product.application.dto.ProductResponseDTO;
import com.sophie.store.backend.context.product.application.dto.ProductUpdateDTO;
import com.sophie.store.backend.context.product.domain.model.Product;
import com.sophie.store.backend.context.product.infrastructure.persistence.ProductEntity;
import com.sophie.store.backend.context.subcategory.application.dto.SubcategoryCreateDTO;
import com.sophie.store.backend.context.subcategory.application.dto.SubcategoryDTO;
import com.sophie.store.backend.context.subcategory.application.dto.SubcategoryResponseDTO;
import com.sophie.store.backend.context.subcategory.application.dto.SubcategoryUpdateDTO;
import com.sophie.store.backend.context.subcategory.domain.model.Subcategory;
import com.sophie.store.backend.context.subcategory.infrastructure.persistence.SubcategoryEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Data
public class ProductData {

    //Correct information
    private final Product productActive = Product.builder()
            .id(1L)
            .name("test")
            .description("test")
            .price(new BigDecimal(1000))
            .quantity(5)
            .qualification(5)
            .subcategory(Subcategory.builder()
                    .id(1L)
                    .name("test")
                    .category(Category.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .state("active")
            .build();

    private final Product productInactive = Product.builder()
            .id(1L)
            .name("test")
            .description("test")
            .price(new BigDecimal(1000))
            .quantity(5)
            .qualification(5)
            .subcategory(Subcategory.builder()
                    .id(1L)
                    .name("test")
                    .category(Category.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .state("inactive")
            .build();

    private final Product productEmpty = Product.builder()
            .id(1L)
            .name("")
            .description("")
            .state("")
            .build();

    private final Product productCreateValid = Product.builder()
            .name("test")
            .description("test")
            .price(new BigDecimal(1000))
            .quantity(5)
            .qualification(5)
            .subcategory(Subcategory.builder()
                    .id(1L)
                    .name("test")
                    .category(Category.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .build();

    private final Product productCreateInvalid = Product.builder().build();

    private final Product productToUpdate = Product.builder()
            .id(1L)
            .name("update")
            .description("test")
            .price(new BigDecimal(1000))
            .quantity(5)
            .qualification(5)
            .subcategory(Subcategory.builder()
                    .id(1L)
                    .name("test")
                    .category(Category.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .build();

    private final Product productUpdated = Product.builder()
            .id(1L)
            .name("update")
            .description("test")
            .price(new BigDecimal(1000))
            .quantity(5)
            .qualification(5)
            .subcategory(Subcategory.builder()
                    .id(1L)
                    .name("test")
                    .category(Category.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .build();

    private final Product productToUpdateNoId = Product.builder()
            .name("update")
            .description("test")
            .price(new BigDecimal(1000))
            .quantity(5)
            .qualification(5)
            .subcategory(Subcategory.builder()
                    .id(1L)
                    .name("test")
                    .category(Category.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .build();

    private final Product productToUpdateInvalid = Product.builder()
            .id(1L)
            .build();

    private final Product productResponseOne = Product.builder()
            .id(1L)
            .name("test")
            .description("test")
            .price(new BigDecimal(1000))
            .quantity(5)
            .qualification(5)
            .subcategory(Subcategory.builder()
                    .id(1L)
                    .name("test")
                    .category(Category.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .build();

    private final Product productResponseTwo = Product.builder()
            .id(2L)
            .name("test2")
            .description("test2")
            .price(new BigDecimal(2000))
            .quantity(10)
            .qualification(4)
            .subcategory(Subcategory.builder()
                    .id(1L)
                    .name("test")
                    .category(Category.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .build();

    private final String encodedPassword = "12345ENCODED";

    private List<Product> productsList;

    //To mapper test
    private final ProductEntity productEntity = ProductEntity.builder()
            .id(1L)
            .name("test")
            .description("test")
            .price(new BigDecimal(1000))
            .quantity(5)
            .qualification(5)
            .subcategory(SubcategoryEntity.builder()
                    .id(1L)
                    .name("test")
                    .category(CategoryEntity.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .state("active")
            .build();

    private final ProductDTO productDTO = ProductDTO.builder()
            .id(1L)
            .name("test")
            .description("test")
            .price(new BigDecimal(1000))
            .quantity(5)
            .qualification(5)
            .subcategory(SubcategoryDTO.builder()
                    .id(1L)
                    .name("test")
                    .category(CategoryDTO.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .build();

    private final ProductCreateDTO productCreateDTO = ProductCreateDTO.builder()
            .name("test")
            .description("test")
            .price(new BigDecimal(1000))
            .quantity(5)
            .qualification(5)
            .build();

    private final ProductUpdateDTO productUpdateDTO = ProductUpdateDTO.builder()
            .id(1L)
            .name("test")
            .description("test")
            .price(new BigDecimal(1000))
            .quantity(5)
            .qualification(5)
            .build();

    private final ProductResponseDTO productResponseDTO = ProductResponseDTO.builder()
            .id(1L)
            .name("test")
            .description("test")
            .price(new BigDecimal(1000))
            .quantity(5)
            .qualification(5)
            .subcategory(SubcategoryResponseDTO.builder()
                    .id(1L)
                    .name("test")
                    .category(CategoryResponseDTO.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .build();

    private final  Product productModel = Product.builder()
            .id(1L)
            .name("test")
            .description("test")
            .price(new BigDecimal(1000))
            .quantity(5)
            .qualification(5)
            .subcategory(Subcategory.builder()
                    .id(1L)
                    .name("test")
                    .category(Category.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .state("active")
            .build();
    
    public ProductData() {
        productsList = new LinkedList<>();
        productsList.add(productResponseOne);
        productsList.add(productResponseTwo);
    }
}
