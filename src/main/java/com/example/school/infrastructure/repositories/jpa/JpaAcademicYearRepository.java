package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.AcademicYearModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaAcademicYearRepository extends JpaRepository<AcademicYearModel, UUID> {
    Optional<AcademicYearModel> findByActiveTrue();
}