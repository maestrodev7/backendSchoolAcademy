package com.example.school.domain.services;

import com.example.school.common.dto.TeacherSubjectDto;
import com.example.school.presenation.validators.TeacherSubjectRequestValidator;

import java.util.List;
import java.util.UUID;

public interface TeacherSubjectServiceInterface {
    TeacherSubjectDto create(TeacherSubjectRequestValidator request);
    TeacherSubjectDto getById(UUID id);
    List<TeacherSubjectDto> getAll();
    List<TeacherSubjectDto> getBySchool(UUID schoolId);
    List<TeacherSubjectDto> getByTeacher(UUID userSchoolId);
    List<TeacherSubjectDto> getBySubject(UUID subjectId);
    List<TeacherSubjectDto> getByTeacherAndSchool(UUID userSchoolId, UUID schoolId);
    List<TeacherSubjectDto> getBySubjectAndSchool(UUID subjectId, UUID schoolId);
    List<TeacherSubjectDto> getByAcademicYear(UUID academicYearId);
    TeacherSubjectDto update(UUID id, TeacherSubjectRequestValidator request);
    void delete(UUID id);
}

