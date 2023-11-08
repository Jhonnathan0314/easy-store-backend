package com.sophie.store.backend.context.category.infrastructure.adapter;

import com.sophie.store.backend.context.category.data.CategoryData;
import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.category.infrastructure.mappers.CategoryMapper;
import com.sophie.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.sophie.store.backend.context.category.infrastructure.persistence.CategoryJpaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryRepositoryJpaAdapterTest {

    @InjectMocks
    private CategoryRepositoryJpaAdapter categoryRepositoryJpaAdapter;


    @Mock
    private CategoryJpaRepository categoryJpaRepository;

    private static CategoryMapper categoryMapper;
    private static CategoryData categoryData;

    @BeforeAll
    static void setUp() {
        categoryData = new CategoryData();
        categoryMapper = new CategoryMapper();
    }

    @Test
    @Order(0)
    void findAllTest() {
        List<CategoryEntity> mockEntities = categoryMapper.modelsToEntities(categoryData.getCategorysList());
        when(categoryJpaRepository.findAll()).thenReturn(mockEntities);

        List<Category> response = categoryRepositoryJpaAdapter.findAll();

        assertNotNull(response);
        assertEquals(mockEntities.size(), response.size());

        verify(categoryJpaRepository).findAll();
    }

    @Test
    @Order(1)
    void findByIdTest() {
        CategoryEntity mockEntity = categoryMapper.modelToEntity(categoryData.getCategoryResponseOne());
        when(categoryJpaRepository.findById(any(Long.class))).thenReturn(Optional.of(mockEntity));

        Category response = categoryRepositoryJpaAdapter.findById(1L).orElse(null);

        assertNotNull(response);
        assertEquals(response, categoryData.getCategoryResponseOne());

        verify(categoryJpaRepository).findById(any(Long.class));
    }

    @Test
    @Order(2)
    void findByNameTest() {
        CategoryEntity mockEntity = categoryMapper.modelToEntity(categoryData.getCategoryResponseOne());
        when(categoryJpaRepository.findByName(any(String.class))).thenReturn(Optional.of(mockEntity));

        Category response = categoryRepositoryJpaAdapter.findByName("test").orElse(null);

        assertNotNull(response);
        assertEquals(response, categoryData.getCategoryResponseOne());

        verify(categoryJpaRepository).findByName(any(String.class));
    }

    @Test
    @Order(3)
    void createTest() {
        CategoryEntity mockEntity = categoryMapper.modelToEntity(categoryData.getCategoryResponseOne());
        when(categoryJpaRepository.save(any(CategoryEntity.class))).thenReturn(mockEntity);

        Category response = categoryRepositoryJpaAdapter.create(categoryData.getCategoryCreateValid());

        assertNotNull(response);
        assertEquals(response, categoryData.getCategoryResponseOne());

        verify(categoryJpaRepository).save(any(CategoryEntity.class));
    }

    @Test
    @Order(4)
    void updateTest() {
        CategoryEntity mockEntity = categoryMapper.modelToEntity(categoryData.getCategoryResponseOne());
        when(categoryJpaRepository.save(any(CategoryEntity.class))).thenReturn(mockEntity);

        Category response = categoryRepositoryJpaAdapter.update(categoryData.getCategoryActive());

        assertNotNull(response);
        assertEquals(response, categoryData.getCategoryResponseOne());

        verify(categoryJpaRepository).save(any(CategoryEntity.class));
    }

    @Test
    @Order(5)
    void deleteByIdTest() {
        categoryRepositoryJpaAdapter.deleteById(1L);

        verify(categoryJpaRepository).deleteById(any(Long.class));
    }
}