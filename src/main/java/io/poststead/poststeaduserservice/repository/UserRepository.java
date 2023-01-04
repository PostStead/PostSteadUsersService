package io.poststead.poststeaduserservice.repository;

import io.poststead.poststeaduserservice.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);

    void deleteByUsername(String username);

    Boolean existsByUsername(String username);

}
