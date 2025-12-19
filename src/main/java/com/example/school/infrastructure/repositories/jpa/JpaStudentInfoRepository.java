package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.StudentInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaStudentInfoRepository extends JpaRepository<StudentInfoModel, UUID> {
    Optional<StudentInfoModel> findByStudentId(UUID studentId);
    boolean existsByUniqueIdentifier(String uniqueIdentifier);
    Optional<StudentInfoModel> findByUniqueIdentifier(String uniqueIdentifier);
}

