package com.example.school.common.mapper;

import com.example.school.common.dto.ClassRoomSubjectDto;
import com.example.school.common.dto.SubjectDto;
import com.example.school.domain.entities.ClassRoomSubject;
import com.example.school.domain.entities.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SubjectMapper {

    public static SubjectDto toDto(Subject entity) {
        if (entity == null) return null;

        SubjectDto dto = new SubjectDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());

        if (entity.getClassRoomSubjects() != null) {
            // Créer une copie snapshot de la collection pour éviter ConcurrentModificationException
            // Utiliser new ArrayList<>() pour créer une copie thread-safe de la collection
            List<ClassRoomSubjectDto> classRoomSubjects = new ArrayList<>(entity.getClassRoomSubjects())
                    .stream()
                    .map(SubjectMapper::toClassRoomSubjectDto)
                    .collect(Collectors.toList());
            dto.setClassRoomSubjects(classRoomSubjects);
        }

        return dto;
    }

    public static ClassRoomSubjectDto toClassRoomSubjectDto(ClassRoomSubject entity) {
        if (entity == null) return null;

        ClassRoomSubjectDto dto = new ClassRoomSubjectDto();
        dto.setId(entity.getId());
        
        if (entity.getSubject() != null) {
            dto.setSubjectId(entity.getSubject().getId());
            dto.setSubjectName(entity.getSubject().getName());
            dto.setSubjectCode(entity.getSubject().getCode());
        }
        
        if (entity.getClassRoom() != null) {
            dto.setClassRoomId(entity.getClassRoom().getId());
            dto.setClassRoomLabel(entity.getClassRoom().getLabel());
        }
        
        dto.setCoefficient(entity.getCoefficient());

        return dto;
    }
}

