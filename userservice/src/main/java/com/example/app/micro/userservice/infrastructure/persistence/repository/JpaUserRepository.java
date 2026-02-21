package com.example.app.micro.userservice.infrastructure.persistence.repository;

import com.example.app.micro.userservice.infrastructure.persistence.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA de Usuario
 * Interface de Spring Data JPA para operaciones de persistencia
 */
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
