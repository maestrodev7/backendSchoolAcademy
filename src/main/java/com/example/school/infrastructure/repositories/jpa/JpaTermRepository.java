package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.TermModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaTermRepository extends JpaRepository<TermModel, UUID> {
    List<TermModel> findByAcademicYearIdOrderByNumberAsc(UUID academicYearId);
    Optional<TermModel> findByAcademicYearIdAndNumber(UUID academicYearId, Integer number);
    
    @Query("SELECT t FROM TermModel t WHERE t.academicYearId = :academicYearId AND t.active = true")
    Optional<TermModel> findActiveByAcademicYearId(@Param("academicYearId") UUID academicYearId);
}

