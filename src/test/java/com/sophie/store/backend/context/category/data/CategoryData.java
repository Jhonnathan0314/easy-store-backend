package com.sophie.store.backend.context.category.data;

import com.sophie.store.backend.context.category.domain.model.Category;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class CategoryData {

    //Correct information
    private final Category categoryActive = Category.builder()
            .id(1L)
            .name("test")
            .state("active")
            .build();

    private final Category categoryInactive = Category.builder()
            .id(1L)
            .name("test")
            .state("inactive")
            .build();

    private final Category categoryEmpty = Category.builder()
            .id(1L)
            .name("")
            .state("")
            .build();

    private final Category categoryCreateValid = Category.builder()
            .name("test")
            .build();

    private final Category categoryCreateInvalid = Category.builder().build();

    private final Category categoryToUpdate = Category.builder()
            .id(1L)
            .name("update")
            .build();

    private final Category categoryUpdated = Category.builder()
            .id(1L)
            .name("update")
            .build();

    private final Category categoryToUpdateNoId = Category.builder()
            .name("update")
            .build();

    private final Category categoryToUpdateInvalid = Category.builder()
            .id(1L)
            .build();

    private final Category categoryResponseOne = Category.builder()
            .id(1L)
            .name("test")
            .build();

    private final Category categoryResponseTwo = Category.builder()
            .id(2L)
            .name("test2")
            .build();

    private final String encodedPassword = "12345ENCODED";

    private List<Category> categorysList;

    public CategoryData() {
        categorysList = new LinkedList<>();
        categorysList.add(categoryResponseOne);
        categorysList.add(categoryResponseTwo);
    }
}
