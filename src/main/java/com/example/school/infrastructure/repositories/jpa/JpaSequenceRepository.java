package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.SequenceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaSequenceRepository extends JpaRepository<SequenceModel, UUID> {
    List<SequenceModel> findByTermIdOrderByNumberAsc(UUID termId);
    Optional<SequenceModel> findByTermIdAndNumber(UUID termId, Integer number);
    
    @Query("SELECT s FROM SequenceModel s WHERE s.termId = :termId AND s.active = true")
    Optional<SequenceModel> findActiveByTermId(@Param("termId") UUID termId);
    
    @Query("SELECT s FROM SequenceModel s JOIN TermModel t ON s.termId = t.id WHERE t.academicYearId = :academicYearId ORDER BY s.number ASC")
    List<SequenceModel> findByAcademicYearId(@Param("academicYearId") UUID academicYearId);
}

