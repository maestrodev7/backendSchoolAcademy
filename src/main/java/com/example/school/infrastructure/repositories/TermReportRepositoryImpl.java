package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.TermReport;
import com.example.school.domain.repositories.TermReportRepositoryInterface;
import com.example.school.infrastructure.mappers.TermReportMapper;
import com.example.school.infrastructure.models.TermReportModel;
import com.example.school.infrastructure.repositories.jpa.JpaTermReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TermReportRepositoryImpl implements TermReportRepositoryInterface {

    private final JpaTermReportRepository jpaTermReportRepository;

    @Override
    public TermReport save(TermReport termReport) {
        TermReportModel model = TermReportMapper.toModel(termReport);
        TermReportModel saved = jpaTermReportRepository.save(model);
        return TermReportMapper.toDomain(saved);
    }

    @Override
    public Optional<TermReport> findById(UUID id) {
        return jpaTermReportRepository.findById(id)
                .map(TermReportMapper::toDomain);
    }

    @Override
    public Optional<TermReport> findByStudentIdAndTermId(UUID studentId, UUID termId) {
        return jpaTermReportRepository.findByStudentIdAndTermId(studentId, termId)
                .map(TermReportMapper::toDomain);
    }

    @Override
    public List<TermReport> findByStudentIdAndAcademicYearId(UUID studentId, UUID academicYearId) {
        return jpaTermReportRepository.findByStudentIdAndAcademicYearId(studentId, academicYearId)
                .stream()
                .map(TermReportMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<TermReport> findByAcademicYearId(UUID academicYearId) {
        return jpaTermReportRepository.findByAcademicYearId(academicYearId)
                .stream()
                .map(TermReportMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaTermReportRepository.deleteById(id);
    }
}

