package com.easy.store.backend.context.subcategory.domain.model;

import com.easy.store.backend.context.category.domain.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subcategory {
    private Long id;
    private String name;
    private Category category;
    private Long createBy;
    private Long updateBy;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private String state;

    public boolean isValid(Subcategory subcategory) {
        if(subcategory.getName() == null || subcategory.getCategory() == null) return false;

        return !subcategory.getName().isEmpty() &&
                subcategory.getCategory().getId() != null;
    }
}
