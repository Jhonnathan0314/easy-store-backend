package com.sophie.store.backend.context.subcategory.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubcategoryJpaRepository extends JpaRepository<SubcategoryEntity, Long> {

    Optional<SubcategoryEntity> findByName(String name);

}
