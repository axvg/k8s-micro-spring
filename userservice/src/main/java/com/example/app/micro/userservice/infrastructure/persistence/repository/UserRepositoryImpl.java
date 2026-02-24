package com.example.app.micro.userservice.infrastructure.persistence.repository;

import com.example.app.micro.userservice.domain.model.User;
import com.example.app.micro.userservice.domain.repository.UserRepository;
import com.example.app.micro.userservice.infrastructure.persistence.entity.RoleEntity;
import com.example.app.micro.userservice.infrastructure.persistence.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final JpaRoleRepository jpaRoleRepository;

    @Override
    public List<User> findAll() {
        log.debug("Finding all users");
        return jpaUserRepository
            .findAll()
            .stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Long id) {
        log.debug("Finding user by id: {}", id);
        return jpaUserRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        log.debug("Finding user by email: {}", email);
        return jpaUserRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public User save(User user) {
        log.debug("Saving user: {}", user.getEmail());
        UserEntity entity = toEntity(user);
        UserEntity savedEntity = jpaUserRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting user by id: {}", id);
        jpaUserRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        log.debug("Checking if email exists: {}", email);
        return jpaUserRepository.existsByEmail(email);
    }

    private User toDomain(UserEntity entity) {
        return User.builder()
            .id(entity.getId())
            .name(entity.getName())
            .email(entity.getEmail())
            .phone(entity.getPhone())
            .address(entity.getAddress())
            .password(entity.getPassword())
            .enabled(entity.getEnabled())
            .roles(entity.getRoles() != null ? entity.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()) : java.util.Collections.emptyList())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }

    private UserEntity toEntity(User user) {
        List<RoleEntity> resolvedRoles = java.util.Collections.emptyList();
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            resolvedRoles = user.getRoles().stream().map(roleName -> {
                return jpaRoleRepository.findByName(roleName)
                    .orElseGet(() -> jpaRoleRepository.save(RoleEntity.builder().name(roleName).build()));
            }).collect(Collectors.toList());
        }

        return UserEntity.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .address(user.getAddress())
            .password(user.getPassword())
            .enabled(user.getEnabled() != null ? user.getEnabled() : true)
            .roles(resolvedRoles)
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
    }
}
