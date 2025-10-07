package com.example.school.domain.repositories;

import com.example.school.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryInterface {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    User save(User user);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<User> findAll();
    Optional<User> findById(String id);
    boolean existsById(String id);
    void deleteById(String id);
}
