package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.SchoolModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JpaSchoolRepository extends JpaRepository<SchoolModel, UUID> {
    boolean existsByName(String name);

    @Query("SELECT DISTINCT s FROM SchoolModel s LEFT JOIN FETCH s.academicYears")
    List<SchoolModel> findAllWithAcademicYears();
    
    @Query("SELECT DISTINCT s FROM SchoolModel s LEFT JOIN FETCH s.academicYears WHERE s.id = :id")
    java.util.Optional<SchoolModel> findByIdWithAcademicYears(@org.springframework.data.repository.query.Param("id") UUID id);
}
