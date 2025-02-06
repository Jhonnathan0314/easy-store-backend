package com.easy.store.backend.context.subcategory.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubcategoryJpaRepository extends JpaRepository<SubcategoryEntity, Long> {

    Optional<SubcategoryEntity> findByName(String name);
    List<SubcategoryEntity> findByCategoryId(Long categoryId);

    @Query(nativeQuery = true, value = "select s.id, s.name, s.create_by, s.creation_date, s.update_by, s.update_date, s.state, s.category_id from subcategory s\n" +
            "inner join category c on c.id = s.category_id\n" +
            "inner join account a on a.id = c.account_id\n" +
            "where a.id = ?1")
    List<SubcategoryEntity> findByAccountId(Long accountId);

}
