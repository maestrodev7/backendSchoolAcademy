package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.Term;
import com.example.school.domain.repositories.TermRepositoryInterface;
import com.example.school.infrastructure.mappers.TermMapper;
import com.example.school.infrastructure.models.TermModel;
import com.example.school.infrastructure.repositories.jpa.JpaTermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TermRepositoryImpl implements TermRepositoryInterface {

    private final JpaTermRepository jpaTermRepository;

    @Override
    public Term save(Term term) {
        TermModel model = TermMapper.toModel(term);
        TermModel saved = jpaTermRepository.save(model);
        return TermMapper.toDomain(saved);
    }

    @Override
    public Optional<Term> findById(UUID id) {
        return jpaTermRepository.findById(id)
                .map(TermMapper::toDomain);
    }

    @Override
    public List<Term> findAll() {
        return jpaTermRepository.findAll().stream()
                .map(TermMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Term> findByAcademicYearId(UUID academicYearId) {
        return jpaTermRepository.findByAcademicYearIdOrderByNumberAsc(academicYearId)
                .stream()
                .map(TermMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Term> findByAcademicYearIdAndNumber(UUID academicYearId, Integer number) {
        return jpaTermRepository.findByAcademicYearIdAndNumber(academicYearId, number)
                .map(TermMapper::toDomain);
    }

    @Override
    public Optional<Term> findActiveByAcademicYearId(UUID academicYearId) {
        return jpaTermRepository.findActiveByAcademicYearId(academicYearId)
                .map(TermMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaTermRepository.deleteById(id);
    }
}

