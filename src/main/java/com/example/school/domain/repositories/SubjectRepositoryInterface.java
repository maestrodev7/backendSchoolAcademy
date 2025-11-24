package com.example.school.domain.repositories;

import com.example.school.domain.entities.Subject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubjectRepositoryInterface {
    Subject save(Subject subject);
    Optional<Subject> findById(UUID id);
    Optional<Subject> findByCode(String code);
    List<Subject> findAll();
    void deleteById(UUID id);
    boolean existsByCode(String code);
}

