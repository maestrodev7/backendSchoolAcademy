package com.example.school.domain.repositories;
import com.example.school.domain.entities.AcademicYear;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AcademicYearRepositoryInterface {
    AcademicYear save(AcademicYear academicYear);
    Optional<AcademicYear> findById(UUID id); // UUID for domain
    List<AcademicYear> findAll();
    void deleteById(UUID id);
    boolean existsById(UUID id);
}
