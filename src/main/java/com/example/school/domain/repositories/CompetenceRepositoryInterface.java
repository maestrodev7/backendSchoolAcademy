package com.example.school.domain.repositories;

import com.example.school.domain.entities.Competence;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompetenceRepositoryInterface {
    Competence save(Competence competence);
    Optional<Competence> findById(UUID id);
    List<Competence> findAll();
    List<Competence> findBySubjectId(UUID subjectId);
    void deleteById(UUID id);
}

