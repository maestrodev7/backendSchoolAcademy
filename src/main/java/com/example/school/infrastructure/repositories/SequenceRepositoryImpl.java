package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.Sequence;
import com.example.school.domain.repositories.SequenceRepositoryInterface;
import com.example.school.infrastructure.mappers.SequenceMapper;
import com.example.school.infrastructure.models.SequenceModel;
import com.example.school.infrastructure.repositories.jpa.JpaSequenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SequenceRepositoryImpl implements SequenceRepositoryInterface {

    private final JpaSequenceRepository jpaSequenceRepository;

    @Override
    public Sequence save(Sequence sequence) {
        SequenceModel model = SequenceMapper.toModel(sequence);
        SequenceModel saved = jpaSequenceRepository.save(model);
        return SequenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Sequence> findById(UUID id) {
        return jpaSequenceRepository.findById(id)
                .map(SequenceMapper::toDomain);
    }

    @Override
    public List<Sequence> findAll() {
        return jpaSequenceRepository.findAll().stream()
                .map(SequenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Sequence> findByTermId(UUID termId) {
        return jpaSequenceRepository.findByTermIdOrderByNumberAsc(termId)
                .stream()
                .map(SequenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Sequence> findByTermIdAndNumber(UUID termId, Integer number) {
        return jpaSequenceRepository.findByTermIdAndNumber(termId, number)
                .map(SequenceMapper::toDomain);
    }

    @Override
    public Optional<Sequence> findActiveByTermId(UUID termId) {
        return jpaSequenceRepository.findActiveByTermId(termId)
                .map(SequenceMapper::toDomain);
    }

    @Override
    public List<Sequence> findByAcademicYearId(UUID academicYearId) {
        return jpaSequenceRepository.findByAcademicYearId(academicYearId)
                .stream()
                .map(SequenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaSequenceRepository.deleteById(id);
    }
}

