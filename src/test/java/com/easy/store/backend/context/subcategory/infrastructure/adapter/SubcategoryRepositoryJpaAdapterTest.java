package com.easy.store.backend.context.subcategory.infrastructure.adapter;

import com.easy.store.backend.context.subcategory.data.SubcategoryData;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.infrastructure.mappers.SubcategoryMapper;
import com.easy.store.backend.context.subcategory.infrastructure.persistence.SubcategoryEntity;
import com.easy.store.backend.context.subcategory.infrastructure.persistence.SubcategoryJpaRepository;
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
class SubcategoryRepositoryJpaAdapterTest {

    @InjectMocks
    private SubcategoryRepositoryJpaAdapter subcategoryRepositoryJpaAdapter;


    @Mock
    private SubcategoryJpaRepository subcategoryJpaRepository;

    private static SubcategoryMapper subcategoryMapper;
    private static SubcategoryData subcategoryData;

    @BeforeAll
    static void setUp() {
        subcategoryData = new SubcategoryData();
        subcategoryMapper = new SubcategoryMapper();
    }

    @Test
    @Order(0)
    void findAllTest() {
        List<SubcategoryEntity> mockEntities = subcategoryMapper.modelsToEntities(subcategoryData.getSubcategorysList());
        when(subcategoryJpaRepository.findAll()).thenReturn(mockEntities);

        List<Subcategory> response = subcategoryRepositoryJpaAdapter.findAll();

        assertNotNull(response);
        assertEquals(mockEntities.size(), response.size());

        verify(subcategoryJpaRepository).findAll();
    }

    @Test
    @Order(1)
    void findByIdTest() {
        SubcategoryEntity mockEntity = subcategoryMapper.modelToEntity(subcategoryData.getSubcategoryResponseOne());
        when(subcategoryJpaRepository.findById(any(Long.class))).thenReturn(Optional.of(mockEntity));

        Subcategory response = subcategoryRepositoryJpaAdapter.findById(1L).orElse(null);

        assertNotNull(response);
        assertEquals(response, subcategoryData.getSubcategoryResponseOne());

        verify(subcategoryJpaRepository).findById(any(Long.class));
    }

    @Test
    @Order(2)
    void findByNameTest() {
        SubcategoryEntity mockEntity = subcategoryMapper.modelToEntity(subcategoryData.getSubcategoryResponseOne());
        when(subcategoryJpaRepository.findByName(any(String.class))).thenReturn(Optional.of(mockEntity));

        Subcategory response = subcategoryRepositoryJpaAdapter.findByName("test").orElse(null);

        assertNotNull(response);
        assertEquals(response, subcategoryData.getSubcategoryResponseOne());

        verify(subcategoryJpaRepository).findByName(any(String.class));
    }

    @Test
    @Order(3)
    void createTest() {
        SubcategoryEntity mockEntity = subcategoryMapper.modelToEntity(subcategoryData.getSubcategoryResponseOne());
        when(subcategoryJpaRepository.save(any(SubcategoryEntity.class))).thenReturn(mockEntity);

        Subcategory response = subcategoryRepositoryJpaAdapter.create(subcategoryData.getSubcategoryCreateValid());

        assertNotNull(response);
        assertEquals(response, subcategoryData.getSubcategoryResponseOne());

        verify(subcategoryJpaRepository).save(any(SubcategoryEntity.class));
    }

    @Test
    @Order(4)
    void updateTest() {
        SubcategoryEntity mockEntity = subcategoryMapper.modelToEntity(subcategoryData.getSubcategoryResponseOne());
        when(subcategoryJpaRepository.save(any(SubcategoryEntity.class))).thenReturn(mockEntity);

        Subcategory response = subcategoryRepositoryJpaAdapter.update(subcategoryData.getSubcategoryActive());

        assertNotNull(response);
        assertEquals(response, subcategoryData.getSubcategoryResponseOne());

        verify(subcategoryJpaRepository).save(any(SubcategoryEntity.class));
    }

    @Test
    @Order(5)
    void deleteByIdTest() {
        subcategoryRepositoryJpaAdapter.deleteById(1L);

        verify(subcategoryJpaRepository).deleteById(any(Long.class));
    }
}