package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.CompetenceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaCompetenceRepository extends JpaRepository<CompetenceModel, UUID> {
    List<CompetenceModel> findBySubjectIdOrderByOrderNumberAsc(UUID subjectId);
}

