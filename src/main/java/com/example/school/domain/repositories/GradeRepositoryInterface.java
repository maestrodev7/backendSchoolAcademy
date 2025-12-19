package com.example.school.domain.repositories;

import com.example.school.domain.entities.Grade;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GradeRepositoryInterface {
    Grade save(Grade grade);
    Optional<Grade> findById(UUID id);
    List<Grade> findAll();
    List<Grade> findByStudentIdAndTermId(UUID studentId, UUID termId);
    List<Grade> findByStudentIdAndSequenceId(UUID studentId, UUID sequenceId);
    List<Grade> findByStudentIdAndAcademicYearId(UUID studentId, UUID academicYearId);
    List<Grade> findBySubjectIdAndStudentIdAndTermId(UUID subjectId, UUID studentId, UUID termId);
    List<Grade> findByTermId(UUID termId);
    List<Grade> findByAcademicYearId(UUID academicYearId);
    Optional<Grade> findByCompetenceIdAndStudentIdAndTermId(UUID competenceId, UUID studentId, UUID termId);
    void deleteById(UUID id);
}

