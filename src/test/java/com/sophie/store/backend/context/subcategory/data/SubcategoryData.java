package com.sophie.store.backend.context.subcategory.data;

import com.sophie.store.backend.context.category.application.dto.CategoryCreateDTO;
import com.sophie.store.backend.context.category.application.dto.CategoryDTO;
import com.sophie.store.backend.context.category.application.dto.CategoryResponseDTO;
import com.sophie.store.backend.context.category.application.dto.CategoryUpdateDTO;
import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.sophie.store.backend.context.subcategory.application.dto.SubcategoryCreateDTO;
import com.sophie.store.backend.context.subcategory.application.dto.SubcategoryDTO;
import com.sophie.store.backend.context.subcategory.application.dto.SubcategoryResponseDTO;
import com.sophie.store.backend.context.subcategory.application.dto.SubcategoryUpdateDTO;
import com.sophie.store.backend.context.subcategory.domain.model.Subcategory;
import com.sophie.store.backend.context.subcategory.infrastructure.persistence.SubcategoryEntity;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class SubcategoryData {

    //Correct information
    private final Subcategory subcategoryActive = Subcategory.builder()
            .id(1L)
            .name("test")
            .category(Category.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .state("active")
            .build();

    private final Subcategory subcategoryInactive = Subcategory.builder()
            .id(1L)
            .name("test")
            .category(Category.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .state("inactive")
            .build();

    private final Subcategory subcategoryEmpty = Subcategory.builder()
            .id(1L)
            .name("")
            .state("")
            .build();

    private final Subcategory subcategoryCreateValid = Subcategory.builder()
            .name("test")
            .category(Category.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .build();

    private final Subcategory subcategoryCreateInvalid = Subcategory.builder().build();

    private final Subcategory subcategoryToUpdate = Subcategory.builder()
            .id(1L)
            .name("update")
            .category(Category.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .build();

    private final Subcategory subcategoryUpdated = Subcategory.builder()
            .id(1L)
            .name("update")
            .category(Category.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .build();

    private final Subcategory subcategoryToUpdateNoId = Subcategory.builder()
            .name("update")
            .category(Category.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .build();

    private final Subcategory subcategoryToUpdateInvalid = Subcategory.builder()
            .id(1L)
            .build();

    private final Subcategory subcategoryResponseOne = Subcategory.builder()
            .id(1L)
            .name("test")
            .category(Category.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .build();

    private final Subcategory subcategoryResponseTwo = Subcategory.builder()
            .id(2L)
            .name("test2")
            .category(Category.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .build();

    private final String encodedPassword = "12345ENCODED";

    private List< Subcategory> subcategorysList;

    //To mapper test
    private final SubcategoryEntity subcategoryEntity = SubcategoryEntity.builder()
            .id(1L)
            .name("test")
            .category(CategoryEntity.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .state("active")
            .build();

    private final SubcategoryDTO subcategoryDTO = SubcategoryDTO.builder()
            .id(1L)
            .name("test")
            .category(CategoryDTO.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .build();

    private final SubcategoryCreateDTO subcategoryCreateDTO = SubcategoryCreateDTO.builder()
            .name("test")
            .category(CategoryCreateDTO.builder()
                    .name("test")
                    .build())
            .build();

    private final SubcategoryUpdateDTO subcategoryUpdateDTO = SubcategoryUpdateDTO.builder()
            .id(1L)
            .name("test")
            .category(CategoryUpdateDTO.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .build();

    private final SubcategoryResponseDTO subcategoryResponseDTO = SubcategoryResponseDTO.builder()
            .id(1L)
            .name("test")
            .category(CategoryResponseDTO.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .build();

    private final Subcategory subcategoryModel = Subcategory.builder()
            .id(1L)
            .name("test")
            .category(Category.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .state("active")
            .build();
    
    public SubcategoryData() {
        subcategorysList = new LinkedList<>();
        subcategorysList.add(subcategoryResponseOne);
        subcategorysList.add(subcategoryResponseTwo);
    }
}
