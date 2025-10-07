package com.example.school.common.mapper;

import com.example.school.common.dto.UserSchoolDto;
import com.example.school.domain.entities.UserSchool;

public class UserSchoolMapper {

    public static UserSchoolDto toDto(UserSchool entity) {
        if (entity == null) return null;

        UserSchoolDto dto = new UserSchoolDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setSchoolId(entity.getSchoolId());
        dto.setRole(entity.getRole());
        return dto;
    }

    public static UserSchool toEntity(UserSchoolDto dto) {
        if (dto == null) return null;

        UserSchool entity = new UserSchool();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setSchoolId(dto.getSchoolId());
        entity.setRole(dto.getRole());
        return entity;
    }
}
