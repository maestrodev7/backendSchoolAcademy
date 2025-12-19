package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.Grade;
import com.example.school.domain.repositories.GradeRepositoryInterface;
import com.example.school.infrastructure.mappers.GradeMapper;
import com.example.school.infrastructure.models.GradeModel;
import com.example.school.infrastructure.repositories.jpa.JpaGradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class GradeRepositoryImpl implements GradeRepositoryInterface {

    private final JpaGradeRepository jpaGradeRepository;

    @Override
    public Grade save(Grade grade) {
        GradeModel model = GradeMapper.toModel(grade);
        GradeModel saved = jpaGradeRepository.save(model);
        return GradeMapper.toDomain(saved);
    }

    @Override
    public Optional<Grade> findById(UUID id) {
        return jpaGradeRepository.findById(id)
                .map(GradeMapper::toDomain);
    }

    @Override
    public List<Grade> findAll() {
        return jpaGradeRepository.findAll()
                .stream()
                .map(GradeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Grade> findByStudentIdAndTermId(UUID studentId, UUID termId) {
        return jpaGradeRepository.findByStudentIdAndTermId(studentId, termId)
                .stream()
                .map(GradeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Grade> findByStudentIdAndSequenceId(UUID studentId, UUID sequenceId) {
        return jpaGradeRepository.findByStudentIdAndSequenceId(studentId, sequenceId)
                .stream()
                .map(GradeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Grade> findByStudentIdAndAcademicYearId(UUID studentId, UUID academicYearId) {
        return jpaGradeRepository.findByStudentIdAndAcademicYearId(studentId, academicYearId)
                .stream()
                .map(GradeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Grade> findBySubjectIdAndStudentIdAndTermId(UUID subjectId, UUID studentId, UUID termId) {
        return jpaGradeRepository.findBySubjectIdAndStudentIdAndTermId(subjectId, studentId, termId)
                .stream()
                .map(GradeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Grade> findByTermId(UUID termId) {
        return jpaGradeRepository.findByTermId(termId)
                .stream()
                .map(GradeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Grade> findByAcademicYearId(UUID academicYearId) {
        return jpaGradeRepository.findByAcademicYearId(academicYearId)
                .stream()
                .map(GradeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Grade> findByCompetenceIdAndStudentIdAndTermId(UUID competenceId, UUID studentId, UUID termId) {
        return jpaGradeRepository.findByCompetenceIdAndStudentIdAndTermId(competenceId, studentId, termId)
                .map(GradeMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaGradeRepository.deleteById(id);
    }
}

