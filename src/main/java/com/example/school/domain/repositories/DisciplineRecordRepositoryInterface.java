package com.example.school.domain.repositories;

import com.example.school.domain.entities.DisciplineRecord;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DisciplineRecordRepositoryInterface {
    DisciplineRecord save(DisciplineRecord disciplineRecord);
    Optional<DisciplineRecord> findById(UUID id);
    Optional<DisciplineRecord> findByStudentIdAndTermId(UUID studentId, UUID termId);
    List<DisciplineRecord> findByStudentIdAndAcademicYearId(UUID studentId, UUID academicYearId);
    List<DisciplineRecord> findByAcademicYearId(UUID academicYearId);
    void deleteById(UUID id);
}

