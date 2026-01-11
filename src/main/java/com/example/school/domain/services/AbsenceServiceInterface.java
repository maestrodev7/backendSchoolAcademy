package com.example.school.domain.services;

import com.example.school.common.dto.AbsenceDto;
import com.example.school.presenation.validators.AbsenceRequestValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AbsenceServiceInterface {
    List<AbsenceDto> createAbsences(AbsenceRequestValidator request);
    AbsenceDto getAbsenceById(UUID id);
    List<AbsenceDto> getAbsencesByStudent(UUID studentId);
    List<AbsenceDto> getAbsencesByStudentAndDateRange(UUID studentId, LocalDate startDate, LocalDate endDate);
    List<AbsenceDto> getAbsencesByClassRoom(UUID classRoomId);
    List<AbsenceDto> getAbsencesByClassRoomAndDate(UUID classRoomId, LocalDate date);
    List<AbsenceDto> getAbsencesByClassRoomAndDateRange(UUID classRoomId, LocalDate startDate, LocalDate endDate);
    List<AbsenceDto> getAbsencesByStudentAndClassRoomAndDateRange(UUID studentId, UUID classRoomId, LocalDate startDate, LocalDate endDate);
    List<AbsenceDto> getAbsencesByClassRoomAndSubjectAndDate(UUID classRoomId, UUID subjectId, LocalDate date);
    List<AbsenceDto> getAbsencesByStudentAndAcademicYear(UUID studentId, UUID academicYearId);
    List<AbsenceDto> getAbsencesByClassRoomAndAcademicYear(UUID classRoomId, UUID academicYearId);
    Double getTotalAbsenceHoursByStudentAndDateRange(UUID studentId, LocalDate startDate, LocalDate endDate);
    void deleteAbsence(UUID id);
}
