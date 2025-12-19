package com.example.school.domain.repositories;

import com.example.school.domain.entities.StudentInfo;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentInfoRepositoryInterface {
    StudentInfo save(StudentInfo studentInfo);
    Optional<StudentInfo> findById(UUID id);
    Optional<StudentInfo> findByStudentId(UUID studentId);
    boolean existsByUniqueIdentifier(String uniqueIdentifier);
    Optional<StudentInfo> findByUniqueIdentifier(String uniqueIdentifier);
    long countByYear(String year);
    List<StudentInfo> findByStudentIds(List<UUID> studentIds);
}

