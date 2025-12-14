package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.AcademicYear;
import com.example.school.domain.repositories.AcademicYearRepositoryInterface;
import com.example.school.infrastructure.mappers.AcademicYearMapper;
import com.example.school.infrastructure.models.AcademicYearModel;
import com.example.school.infrastructure.repositories.jpa.JpaAcademicYearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AcademicYearRepositoryImpl implements AcademicYearRepositoryInterface {

    private final JpaAcademicYearRepository jpaAcademicYearRepository;

    @Override
    public AcademicYear save(AcademicYear year) {
        AcademicYearModel model = AcademicYearMapper.toModel(year);
        AcademicYearModel saved = jpaAcademicYearRepository.save(model);
        return AcademicYearMapper.toDomain(saved);
    }

    @Override
    public Optional<AcademicYear> findById(UUID id) {
        return jpaAcademicYearRepository.findByIdWithSchools(id)
                .map(AcademicYearMapper::toDomain);
    }
    
    public boolean existsByAcademicYearIdAndSchoolId(UUID academicYearId, UUID schoolId) {
        return jpaAcademicYearRepository.existsByAcademicYearIdAndSchoolId(academicYearId, schoolId);
    }

    @Override
    public List<AcademicYear> findAll() {
        return jpaAcademicYearRepository.findAll()
                .stream()
                .map(AcademicYearMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaAcademicYearRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaAcademicYearRepository.existsById(id);
    }

    public Optional<AcademicYear> findActiveYear() {
        return jpaAcademicYearRepository.findByActiveTrue()
                .map(AcademicYearMapper::toDomain);
    }
}
