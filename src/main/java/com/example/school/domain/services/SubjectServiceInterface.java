package com.example.school.domain.services;

import com.example.school.common.dto.SubjectDto;
import com.example.school.presenation.validators.SubjectRequestValidator;

import java.util.List;
import java.util.UUID;

public interface SubjectServiceInterface {
    SubjectDto createSubject(SubjectRequestValidator request);
    List<SubjectDto> getAllSubjects();
    SubjectDto getSubjectById(UUID id);
    SubjectDto updateSubject(UUID id, SubjectRequestValidator request);
    void deleteSubject(UUID id);
    List<SubjectDto> getSubjectsByClassroom(UUID classroomId);
}

