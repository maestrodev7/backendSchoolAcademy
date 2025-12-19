package com.example.school.domain.services;

import com.example.school.common.dto.DisciplineRecordDto;
import com.example.school.presenation.validators.DisciplineRecordRequestValidator;

import java.util.List;
import java.util.UUID;

public interface DisciplineRecordServiceInterface {
    DisciplineRecordDto createDisciplineRecord(DisciplineRecordRequestValidator request);
    DisciplineRecordDto getDisciplineRecordById(UUID id);
    DisciplineRecordDto getDisciplineRecordByStudentIdAndTermId(UUID studentId, UUID termId);
    List<DisciplineRecordDto> getDisciplineRecordsByStudentIdAndAcademicYearId(UUID studentId, UUID academicYearId);
    List<DisciplineRecordDto> getDisciplineRecordsByAcademicYearId(UUID academicYearId);
    DisciplineRecordDto updateDisciplineRecord(UUID id, DisciplineRecordRequestValidator request);
    void deleteDisciplineRecord(UUID id);
}

