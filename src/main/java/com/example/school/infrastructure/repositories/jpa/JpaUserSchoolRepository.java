package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.UserSchoolModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaUserSchoolRepository extends JpaRepository<UserSchoolModel, UUID> {

    List<UserSchoolModel> findByUserId(UUID userId);

    List<UserSchoolModel> findBySchoolId(UUID schoolId);

    List<UserSchoolModel> findByRole(String role);

    @Query("SELECT u FROM UserSchoolModel u WHERE UPPER(u.role) IN ('ADMIN', 'PROMOTEUR')")
    List<UserSchoolModel> findAllAdminsOrPromoteurs();

    Optional<UserSchoolModel> findByUserIdAndSchoolId(UUID userId, UUID schoolId);
}
