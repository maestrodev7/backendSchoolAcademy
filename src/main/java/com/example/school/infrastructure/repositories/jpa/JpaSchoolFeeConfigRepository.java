package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.SchoolFeeConfigModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaSchoolFeeConfigRepository extends JpaRepository<SchoolFeeConfigModel, UUID> {
    Optional<SchoolFeeConfigModel> findBySchool_Id(UUID schoolId);
}

