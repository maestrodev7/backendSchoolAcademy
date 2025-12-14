package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.AcademicYearModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JpaAcademicYearRepository extends JpaRepository<AcademicYearModel, UUID> {
    Optional<AcademicYearModel> findByActiveTrue();
    
    @Query("SELECT DISTINCT a FROM AcademicYearModel a LEFT JOIN FETCH a.schools WHERE a.id = :id")
    Optional<AcademicYearModel> findByIdWithSchools(@Param("id") UUID id);
    
    @Query("SELECT COUNT(s) > 0 FROM SchoolModel s JOIN s.academicYears a WHERE a.id = :academicYearId AND s.id = :schoolId")
    boolean existsByAcademicYearIdAndSchoolId(@Param("academicYearId") UUID academicYearId, @Param("schoolId") UUID schoolId);
}