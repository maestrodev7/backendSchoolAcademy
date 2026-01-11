package com.example.school.domain.repositories;

import com.example.school.domain.entities.Absence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AbsenceRepositoryInterface {
    Absence save(Absence absence);
    Optional<Absence> findById(UUID id);
    List<Absence> findByStudentId(UUID studentId);
    List<Absence> findByStudentIdAndDateBetween(UUID studentId, LocalDate startDate, LocalDate endDate);
    List<Absence> findByClassRoomId(UUID classRoomId);
    List<Absence> findByClassRoomIdAndDate(UUID classRoomId, LocalDate date);
    List<Absence> findByClassRoomIdAndDateBetween(UUID classRoomId, LocalDate startDate, LocalDate endDate);
    List<Absence> findByStudentIdAndClassRoomIdAndDateBetween(UUID studentId, UUID classRoomId, LocalDate startDate, LocalDate endDate);
    List<Absence> findByClassRoomIdAndSubjectIdAndDate(UUID classRoomId, UUID subjectId, LocalDate date);
    List<Absence> findByStudentIdAndAcademicYearId(UUID studentId, UUID academicYearId);
    List<Absence> findByClassRoomIdAndAcademicYearId(UUID classRoomId, UUID academicYearId);
    List<Absence> findBySchoolIdAndDateBetween(UUID schoolId, LocalDate startDate, LocalDate endDate);
    Double sumHoursByStudentAndDateRange(UUID studentId, LocalDate startDate, LocalDate endDate);
    void deleteById(UUID id);
}
