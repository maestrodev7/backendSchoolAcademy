package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.TermReportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaTermReportRepository extends JpaRepository<TermReportModel, UUID> {
    Optional<TermReportModel> findByStudentIdAndTermId(UUID studentId, UUID termId);
    
    @Query("SELECT t FROM TermReportModel t WHERE t.student.id = :studentId AND t.academicYear.id = :academicYearId")
    List<TermReportModel> findByStudentIdAndAcademicYearId(@Param("studentId") UUID studentId, @Param("academicYearId") UUID academicYearId);
    
    @Query("SELECT t FROM TermReportModel t WHERE t.academicYear.id = :academicYearId")
    List<TermReportModel> findByAcademicYearId(@Param("academicYearId") UUID academicYearId);
}

