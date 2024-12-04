package com.easy.store.backend.context.category.data;

import com.easy.store.backend.context.category.application.dto.CategoryCreateDTO;
import com.easy.store.backend.context.category.application.dto.CategoryDTO;
import com.easy.store.backend.context.category.application.dto.CategoryResponseDTO;
import com.easy.store.backend.context.category.application.dto.CategoryUpdateDTO;
import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class CategoryData {

    //Correct information
    private final Category categoryActive = Category.builder()
            .id(1L)
            .name("test")
            .description("test")
            .imageName("test.png")
            .state("active")
            .build();

    private final Category categoryInactive = Category.builder()
            .id(1L)
            .name("test")
            .description("test")
            .imageName("test.png")
            .state("inactive")
            .build();

    private final Category categoryEmpty = Category.builder()
            .id(1L)
            .name("")
            .description("")
            .imageName("test.png")
            .state("")
            .build();

    private final Category categoryCreateValid = Category.builder()
            .name("test")
            .description("test")
            .imageName("test.png")
            .build();

    private final Category categoryCreateInvalid = Category.builder().build();

    private final Category categoryToUpdate = Category.builder()
            .id(1L)
            .name("update")
            .description("update")
            .imageName("update.png")
            .build();

    private final Category categoryUpdated = Category.builder()
            .id(1L)
            .name("update")
            .description("update")
            .imageName("update.png")
            .build();

    private final Category categoryToUpdateNoId = Category.builder()
            .name("update")
            .description("update")
            .imageName("update.png")
            .build();

    private final Category categoryToUpdateInvalid = Category.builder()
            .id(1L)
            .build();

    private final Category categoryResponseOne = Category.builder()
            .id(1L)
            .name("test")
            .description("test")
            .imageName("test.png")
            .build();

    private final Category categoryResponseTwo = Category.builder()
            .id(2L)
            .name("test2")
            .description("test2")
            .imageName("test2.png")
            .build();

    private final String encodedPassword = "12345ENCODED";

    private List<Category> categorysList;

    //To mapper test
    private final CategoryEntity categoryEntity = CategoryEntity.builder()
            .id(1L)
            .name("test")
            .description("test")
            .state("active")
            .build();

    private final CategoryDTO categoryDTO = CategoryDTO.builder()
            .id(1L)
            .name("test")
            .description("test")
            .build();

    private final CategoryCreateDTO categoryCreateDTO = CategoryCreateDTO.builder()
            .name("test")
            .description("test")
            .build();

    private final CategoryUpdateDTO categoryUpdateDTO = CategoryUpdateDTO.builder()
            .id(1L)
            .name("test")
            .description("test")
            .build();

    private final CategoryResponseDTO categoryResponseDTO = CategoryResponseDTO.builder()
            .id(1L)
            .name("test")
            .description("test")
            .build();

    private final Category categoryModel = Category.builder()
            .id(1L)
            .name("test")
            .description("test")
            .state("active")
            .build();
    
    public CategoryData() {
        categorysList = new LinkedList<>();
        categorysList.add(categoryResponseOne);
        categorysList.add(categoryResponseTwo);
    }
}
