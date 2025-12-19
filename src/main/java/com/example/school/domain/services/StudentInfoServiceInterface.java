package com.example.school.domain.services;

import com.example.school.common.dto.StudentInfoDto;
import com.example.school.presenation.validators.StudentInfoRequestValidator;

import java.util.List;
import java.util.UUID;

public interface StudentInfoServiceInterface {
    StudentInfoDto createStudentInfo(StudentInfoRequestValidator request);
    StudentInfoDto getStudentInfoById(UUID id);
    StudentInfoDto getStudentInfoByStudentId(UUID studentId);
    StudentInfoDto updateStudentInfo(UUID id, StudentInfoRequestValidator request);
    List<StudentInfoDto> getStudentInfosByAcademicYear(UUID academicYearId);
    List<StudentInfoDto> getStudentInfosByClassRoomAndAcademicYear(UUID classRoomId, UUID academicYearId);
}

