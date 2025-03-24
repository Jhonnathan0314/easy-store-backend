package com.easy.store.backend.context.codes.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CodeJpaRepository extends JpaRepository<CodeEntity, Long> {

    CodeEntity findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from codes where user_id = ?1")
    void deleteByUserId(Long userId);

}
