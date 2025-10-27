package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserModel, UUID> {

    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query(value = """
        SELECT DISTINCT u.*
        FROM users u
        LEFT JOIN user_roles r ON u.id = r.user_id
        WHERE (:username IS NULL OR u.username ILIKE CONCAT('%', :username, '%'))
          AND (:email IS NULL OR u.email ILIKE CONCAT('%', :email, '%'))
          AND (:phoneNumber IS NULL OR u.phone_number ILIKE CONCAT('%', :phoneNumber, '%'))
          AND (:role IS NULL OR r.role ILIKE :role)
        """,
            countQuery = """
        SELECT COUNT(DISTINCT u.id)
        FROM users u
        LEFT JOIN user_roles r ON u.id = r.user_id
        WHERE (:username IS NULL OR u.username ILIKE CONCAT('%', :username, '%'))
          AND (:email IS NULL OR u.email ILIKE CONCAT('%', :email, '%'))
          AND (:phoneNumber IS NULL OR u.phone_number ILIKE CONCAT('%', :phoneNumber, '%'))
          AND (:role IS NULL OR r.role ILIKE :role)
        """,
            nativeQuery = true)
    Page<UserModel> filterUsers(
            @Param("username") String username,
            @Param("email") String email,
            @Param("phoneNumber") String phoneNumber,
            @Param("role") String role,
            Pageable pageable
    );
}
