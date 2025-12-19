package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.GradeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaGradeRepository extends JpaRepository<GradeModel, UUID> {
    
    List<GradeModel> findByStudentIdAndTermId(UUID studentId, UUID termId);
    
    List<GradeModel> findByStudentIdAndSequenceId(UUID studentId, UUID sequenceId);
    
    @Query("SELECT g FROM GradeModel g WHERE g.student.id = :studentId AND g.term.academicYearId = :academicYearId")
    List<GradeModel> findByStudentIdAndAcademicYearId(@Param("studentId") UUID studentId, @Param("academicYearId") UUID academicYearId);
    
    @Query("SELECT g FROM GradeModel g WHERE g.competence.subject.id = :subjectId AND g.student.id = :studentId AND g.term.id = :termId")
    List<GradeModel> findBySubjectIdAndStudentIdAndTermId(@Param("subjectId") UUID subjectId, @Param("studentId") UUID studentId, @Param("termId") UUID termId);
    
    @Query("SELECT g FROM GradeModel g WHERE g.term.id = :termId")
    List<GradeModel> findByTermId(@Param("termId") UUID termId);
    
    @Query("SELECT g FROM GradeModel g WHERE g.term.academicYearId = :academicYearId")
    List<GradeModel> findByAcademicYearId(@Param("academicYearId") UUID academicYearId);
    
    @Query("SELECT g FROM GradeModel g WHERE g.competence.id = :competenceId AND g.student.id = :studentId AND g.term.id = :termId")
    Optional<GradeModel> findByCompetenceIdAndStudentIdAndTermId(@Param("competenceId") UUID competenceId, @Param("studentId") UUID studentId, @Param("termId") UUID termId);
}

