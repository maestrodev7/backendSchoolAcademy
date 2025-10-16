package com.example.school.domain.services;

import com.example.school.common.dto.ClassLevelDto;
import com.example.school.presenation.validators.ClassLevelRequestValidator;

import java.util.List;
import java.util.UUID;

public interface ClassLevelServiceInterface {
    ClassLevelDto createClassLevel(ClassLevelRequestValidator request);
    List<ClassLevelDto> getAllClassLevels();
    ClassLevelDto getClassLevelById(UUID id);
    ClassLevelDto updateClassLevel(UUID id, ClassLevelRequestValidator request);
    void deleteClassLevel(UUID id);
}
