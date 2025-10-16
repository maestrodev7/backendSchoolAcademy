package com.example.school.domain.repositories;

import com.example.school.domain.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryInterface {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    User save(User user);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<User> findAll();
    Optional<User> findById(UUID id);
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
