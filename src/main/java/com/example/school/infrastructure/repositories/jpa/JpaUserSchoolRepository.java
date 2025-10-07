package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.UserSchoolModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaUserSchoolRepository extends JpaRepository<UserSchoolModel, UUID> {

    List<UserSchoolModel> findByUserId(UUID userId);

    List<UserSchoolModel> findBySchoolId(UUID schoolId);

    List<UserSchoolModel> findByRole(String role);

    Optional<UserSchoolModel> findByUserIdAndSchoolId(UUID userId, UUID schoolId);
}
