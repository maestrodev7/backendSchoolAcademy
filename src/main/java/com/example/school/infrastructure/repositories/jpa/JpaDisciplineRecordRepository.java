package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.DisciplineRecordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaDisciplineRecordRepository extends JpaRepository<DisciplineRecordModel, UUID> {
    Optional<DisciplineRecordModel> findByStudentIdAndTermId(UUID studentId, UUID termId);
    
    @Query("SELECT d FROM DisciplineRecordModel d WHERE d.student.id = :studentId AND d.term.academicYearId = :academicYearId")
    List<DisciplineRecordModel> findByStudentIdAndAcademicYearId(@Param("studentId") UUID studentId, @Param("academicYearId") UUID academicYearId);
    
    @Query("SELECT d FROM DisciplineRecordModel d WHERE d.term.academicYearId = :academicYearId")
    List<DisciplineRecordModel> findByAcademicYearId(@Param("academicYearId") UUID academicYearId);
}

