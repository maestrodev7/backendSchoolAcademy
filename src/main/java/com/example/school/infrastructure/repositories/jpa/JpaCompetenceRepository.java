package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.CompetenceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaCompetenceRepository extends JpaRepository<CompetenceModel, UUID> {
    List<CompetenceModel> findBySubjectIdOrderByOrderNumberAsc(UUID subjectId);

    @Query("SELECT COALESCE(MAX(c.orderNumber), 0) FROM CompetenceModel c WHERE c.subject.id = :subjectId")
    Integer findMaxOrderNumberBySubjectId(@Param("subjectId") UUID subjectId);
}

