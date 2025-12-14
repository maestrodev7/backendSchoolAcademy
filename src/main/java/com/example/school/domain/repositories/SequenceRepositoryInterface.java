package com.example.school.domain.repositories;

import com.example.school.domain.entities.Sequence;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SequenceRepositoryInterface {
    Sequence save(Sequence sequence);
    Optional<Sequence> findById(UUID id);
    List<Sequence> findAll();
    List<Sequence> findByTermId(UUID termId);
    Optional<Sequence> findByTermIdAndNumber(UUID termId, Integer number);
    Optional<Sequence> findActiveByTermId(UUID termId);
    List<Sequence> findByAcademicYearId(UUID academicYearId);
    void deleteById(UUID id);
}

