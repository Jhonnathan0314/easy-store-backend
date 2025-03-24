package com.easy.store.backend.context.product.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByName(String name);
    Optional<ProductEntity> findById(Long id);
    List<ProductEntity> findBySubcategoryId(Long subcategoryId);

    @Query(nativeQuery = true, value = "select p.id, p.name, p.price, p.quantity, p.qualification, p.description, " +
            "p.create_by, p.creation_date, p.update_by, p.update_date, p.state, p.subcategory_id, p.image_name, " +
            "p.image_number, p.image_last_number, c.id as category_id from product p\n" +
            "inner join subcategory s on s.id = p.subcategory_id\n" +
            "inner join category c on c.id = s.category_id\n" +
            "where c.id = ?1")
    List<ProductEntity> findByCategoryId(Long categoryId);

    @Query(nativeQuery = true, value = "select p.id, p.name, p.price, p.quantity, p.qualification, p.description, " +
            "p.create_by, p.creation_date, p.update_by, p.update_date, p.state, p.subcategory_id, p.image_name, " +
            "p.image_number, p.image_last_number, s.category_id from product p\n" +
            "inner join subcategory s on s.id = p.subcategory_id\n" +
            "inner join category c on c.id = s.category_id\n" +
            "inner join account a on a.id = c.account_id\n" +
            "where a.id = ?1")
    List<ProductEntity> findByAccountId(Long accountId);

}
