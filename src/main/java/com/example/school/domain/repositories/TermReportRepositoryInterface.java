package com.example.school.domain.repositories;

import com.example.school.domain.entities.TermReport;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TermReportRepositoryInterface {
    TermReport save(TermReport termReport);
    Optional<TermReport> findById(UUID id);
    Optional<TermReport> findByStudentIdAndTermId(UUID studentId, UUID termId);
    List<TermReport> findByStudentIdAndAcademicYearId(UUID studentId, UUID academicYearId);
    List<TermReport> findByAcademicYearId(UUID academicYearId);
    void deleteById(UUID id);
}

