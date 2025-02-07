package com.easy.store.backend.context.user.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    @Query(nativeQuery = true, value = "select u.id, u.role_id, u.username, u.name, u.last_name, u.password, u.creation_date, u.update_date, u.state\n" +
            "from user u\n" +
            "inner join account_has_user ahu on ahu.user_id = u.id\n" +
            "inner join account a on a.id = ahu.account_id\n" +
            "where a.id = ?1")
    List<UserEntity> findByAccountId(Long accountId);

}
