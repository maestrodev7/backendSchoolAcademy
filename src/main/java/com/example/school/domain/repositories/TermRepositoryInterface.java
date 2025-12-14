package com.example.school.domain.repositories;

import com.example.school.domain.entities.Term;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TermRepositoryInterface {
    Term save(Term term);
    Optional<Term> findById(UUID id);
    List<Term> findAll();
    List<Term> findByAcademicYearId(UUID academicYearId);
    Optional<Term> findByAcademicYearIdAndNumber(UUID academicYearId, Integer number);
    Optional<Term> findActiveByAcademicYearId(UUID academicYearId);
    void deleteById(UUID id);
}

