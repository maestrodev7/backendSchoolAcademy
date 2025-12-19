package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.DisciplineRecord;
import com.example.school.domain.repositories.DisciplineRecordRepositoryInterface;
import com.example.school.infrastructure.mappers.DisciplineRecordMapper;
import com.example.school.infrastructure.models.DisciplineRecordModel;
import com.example.school.infrastructure.repositories.jpa.JpaDisciplineRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DisciplineRecordRepositoryImpl implements DisciplineRecordRepositoryInterface {

    private final JpaDisciplineRecordRepository jpaDisciplineRecordRepository;

    @Override
    public DisciplineRecord save(DisciplineRecord disciplineRecord) {
        DisciplineRecordModel model = DisciplineRecordMapper.toModel(disciplineRecord);
        DisciplineRecordModel saved = jpaDisciplineRecordRepository.save(model);
        return DisciplineRecordMapper.toDomain(saved);
    }

    @Override
    public Optional<DisciplineRecord> findById(UUID id) {
        return jpaDisciplineRecordRepository.findById(id)
                .map(DisciplineRecordMapper::toDomain);
    }

    @Override
    public Optional<DisciplineRecord> findByStudentIdAndTermId(UUID studentId, UUID termId) {
        return jpaDisciplineRecordRepository.findByStudentIdAndTermId(studentId, termId)
                .map(DisciplineRecordMapper::toDomain);
    }

    @Override
    public List<DisciplineRecord> findByStudentIdAndAcademicYearId(UUID studentId, UUID academicYearId) {
        return jpaDisciplineRecordRepository.findByStudentIdAndAcademicYearId(studentId, academicYearId)
                .stream()
                .map(DisciplineRecordMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<DisciplineRecord> findByAcademicYearId(UUID academicYearId) {
        return jpaDisciplineRecordRepository.findByAcademicYearId(academicYearId)
                .stream()
                .map(DisciplineRecordMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaDisciplineRecordRepository.deleteById(id);
    }
}

