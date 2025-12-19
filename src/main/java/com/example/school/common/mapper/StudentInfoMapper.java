package com.example.school.common.mapper;

import com.example.school.common.dto.StudentInfoDto;
import com.example.school.domain.entities.StudentInfo;

public class StudentInfoMapper {
    public static StudentInfoDto toDto(StudentInfo info) {
        if (info == null) return null;
        StudentInfoDto dto = new StudentInfoDto();
        dto.setId(info.getId());
        dto.setStudentId(info.getStudent() != null ? info.getStudent().getId() : null);
        dto.setStudentName(info.getStudent() != null 
                ? (info.getStudent().getFirstName() + " " + info.getStudent().getLastName()).trim() : null);
        dto.setUniqueIdentifier(info.getUniqueIdentifier());
        dto.setBirthDate(info.getBirthDate());
        dto.setBirthPlace(info.getBirthPlace());
        dto.setGender(info.getGender());
        dto.setIsRepeating(info.isRepeating());
        dto.setParentNames(info.getParentNames());
        dto.setParentContacts(info.getParentContacts());
        dto.setPhotoUrl(info.getPhotoUrl());
        return dto;
    }
}

