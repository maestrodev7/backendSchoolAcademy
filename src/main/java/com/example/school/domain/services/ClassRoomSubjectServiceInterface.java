package com.example.school.domain.services;

import com.example.school.common.dto.ClassRoomSubjectDto;
import com.example.school.presenation.validators.ClassRoomSubjectRequestValidator;
import com.example.school.presenation.validators.UpdateClassRoomSubjectValidator;

import java.util.List;
import java.util.UUID;

public interface ClassRoomSubjectServiceInterface {
    ClassRoomSubjectDto create(ClassRoomSubjectRequestValidator request);
    ClassRoomSubjectDto getById(UUID id);
    List<ClassRoomSubjectDto> getAll();
    ClassRoomSubjectDto update(UUID id, UpdateClassRoomSubjectValidator request);
    void delete(UUID id);
    void deleteByClassRoomAndSubject(UUID classRoomId, UUID subjectId);
    List<ClassRoomSubjectDto> getByClassRoom(UUID classRoomId);
    List<ClassRoomSubjectDto> getBySubject(UUID subjectId);
    ClassRoomSubjectDto getByClassRoomAndSubject(UUID classRoomId, UUID subjectId);
}

