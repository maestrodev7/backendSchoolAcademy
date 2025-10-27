package com.example.school.domain.repositories;

import com.example.school.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    Page<User> filterUsers(String username, String email, String role, String phoneNumber, Pageable pageable);
    Optional<User> findById(UUID id);
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
