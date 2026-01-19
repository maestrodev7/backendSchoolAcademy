package com.example.school.domain.services;

import com.example.school.common.dto.GradeDto;
import com.example.school.presenation.validators.ClassGradesBulkRequest;
import com.example.school.presenation.validators.GradeRequestValidator;

import java.util.List;
import java.util.UUID;

public interface GradeServiceInterface {
    GradeDto createGrade(GradeRequestValidator request);
    GradeDto getGradeById(UUID id);
    List<GradeDto> getAllGrades();
    List<GradeDto> getGradesByStudentIdAndTermId(UUID studentId, UUID termId);
    List<GradeDto> getGradesByStudentIdAndSequenceId(UUID studentId, UUID sequenceId);
    List<GradeDto> getGradesByStudentIdAndAcademicYearId(UUID studentId, UUID academicYearId);
    GradeDto updateGrade(UUID id, GradeRequestValidator request);
    void deleteGrade(UUID id);
    void saveOrUpdateClassGrades(UUID classRoomId, ClassGradesBulkRequest request);
}

