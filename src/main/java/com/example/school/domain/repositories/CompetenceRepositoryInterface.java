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

    /**
     * Renvoie le prochain numéro d'ordre pour une compétence d'une matière donnée.
     * Exemple : si les compétences existantes ont les ordres 1,2,3 alors on renvoie 4.
     */
    Integer getNextOrderNumberForSubject(UUID subjectId);
}

