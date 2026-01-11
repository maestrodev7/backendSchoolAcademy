package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.Absence;
import com.example.school.domain.repositories.AbsenceRepositoryInterface;
import com.example.school.infrastructure.mappers.AbsenceMapper;
import com.example.school.infrastructure.models.AbsenceModel;
import com.example.school.infrastructure.repositories.jpa.JpaAbsenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AbsenceRepositoryImpl implements AbsenceRepositoryInterface {

    private final JpaAbsenceRepository jpaAbsenceRepository;

    @Override
    public Absence save(Absence absence) {
        AbsenceModel model = AbsenceMapper.toModel(absence);
        AbsenceModel saved = jpaAbsenceRepository.save(model);
        return AbsenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Absence> findById(UUID id) {
        return jpaAbsenceRepository.findById(id)
                .map(AbsenceMapper::toDomain);
    }

    @Override
    public List<Absence> findByStudentId(UUID studentId) {
        return jpaAbsenceRepository.findByStudentId(studentId)
                .stream()
                .map(AbsenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Absence> findByStudentIdAndDateBetween(UUID studentId, LocalDate startDate, LocalDate endDate) {
        return jpaAbsenceRepository.findByStudentIdAndDateBetween(studentId, startDate, endDate)
                .stream()
                .map(AbsenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Absence> findByClassRoomId(UUID classRoomId) {
        return jpaAbsenceRepository.findByClassRoomId(classRoomId)
                .stream()
                .map(AbsenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Absence> findByClassRoomIdAndDate(UUID classRoomId, LocalDate date) {
        return jpaAbsenceRepository.findByClassRoomIdAndDate(classRoomId, date)
                .stream()
                .map(AbsenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Absence> findByClassRoomIdAndDateBetween(UUID classRoomId, LocalDate startDate, LocalDate endDate) {
        return jpaAbsenceRepository.findByClassRoomIdAndDateBetween(classRoomId, startDate, endDate)
                .stream()
                .map(AbsenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Absence> findByStudentIdAndClassRoomIdAndDateBetween(UUID studentId, UUID classRoomId, LocalDate startDate, LocalDate endDate) {
        return jpaAbsenceRepository.findByStudentIdAndClassRoomIdAndDateBetween(studentId, classRoomId, startDate, endDate)
                .stream()
                .map(AbsenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Absence> findByClassRoomIdAndSubjectIdAndDate(UUID classRoomId, UUID subjectId, LocalDate date) {
        return jpaAbsenceRepository.findByClassRoomIdAndSubjectIdAndDate(classRoomId, subjectId, date)
                .stream()
                .map(AbsenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Absence> findByStudentIdAndAcademicYearId(UUID studentId, UUID academicYearId) {
        return jpaAbsenceRepository.findByStudentIdAndAcademicYearId(studentId, academicYearId)
                .stream()
                .map(AbsenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Absence> findByClassRoomIdAndAcademicYearId(UUID classRoomId, UUID academicYearId) {
        return jpaAbsenceRepository.findByClassRoomIdAndAcademicYearId(classRoomId, academicYearId)
                .stream()
                .map(AbsenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Absence> findBySchoolIdAndDateBetween(UUID schoolId, LocalDate startDate, LocalDate endDate) {
        return jpaAbsenceRepository.findBySchoolIdAndDateBetween(schoolId, startDate, endDate)
                .stream()
                .map(AbsenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Double sumHoursByStudentAndDateRange(UUID studentId, LocalDate startDate, LocalDate endDate) {
        Double sum = jpaAbsenceRepository.sumHoursByStudentAndDateRange(studentId, startDate, endDate);
        return sum != null ? sum : 0.0;
    }

    @Override
    public void deleteById(UUID id) {
        jpaAbsenceRepository.deleteById(id);
    }
}
