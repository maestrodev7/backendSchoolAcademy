package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.Competence;
import com.example.school.domain.repositories.CompetenceRepositoryInterface;
import com.example.school.infrastructure.mappers.CompetenceMapper;
import com.example.school.infrastructure.models.CompetenceModel;
import com.example.school.infrastructure.repositories.jpa.JpaCompetenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CompetenceRepositoryImpl implements CompetenceRepositoryInterface {

    private final JpaCompetenceRepository jpaCompetenceRepository;

    @Override
    public Competence save(Competence competence) {
        CompetenceModel model = CompetenceMapper.toModel(competence);
        CompetenceModel saved = jpaCompetenceRepository.save(model);
        return CompetenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Competence> findById(UUID id) {
        return jpaCompetenceRepository.findById(id)
                .map(CompetenceMapper::toDomain);
    }

    @Override
    public List<Competence> findAll() {
        return jpaCompetenceRepository.findAll()
                .stream()
                .map(CompetenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Competence> findBySubjectId(UUID subjectId) {
        return jpaCompetenceRepository.findBySubjectIdOrderByOrderNumberAsc(subjectId)
                .stream()
                .map(CompetenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaCompetenceRepository.deleteById(id);
    }

    @Override
    public Integer getNextOrderNumberForSubject(UUID subjectId) {
        Integer max = jpaCompetenceRepository.findMaxOrderNumberBySubjectId(subjectId);
        // Si aucune compétence n'existe encore pour cette matière, on commence à 1
        return (max == null ? 1 : max + 1);
    }
}

