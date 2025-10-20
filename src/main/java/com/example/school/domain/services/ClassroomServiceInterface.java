package com.example.school.domain.services;

import com.example.school.common.dto.ClassroomDto;
import com.example.school.presenation.validators.ClassroomRequestValidator;

import java.util.List;
import java.util.UUID;

public interface ClassroomServiceInterface {
    ClassroomDto createClassroom(ClassroomRequestValidator request);
    List<ClassroomDto> getClassroomsBySchool(UUID id);
    ClassroomDto getClassroomById(UUID id);
    ClassroomDto updateClassroom(UUID id, ClassroomRequestValidator request);
    void deleteClassroom(UUID id);
}
