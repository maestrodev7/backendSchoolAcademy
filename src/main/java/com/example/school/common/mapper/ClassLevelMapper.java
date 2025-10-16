package com.example.school.common.mapper;

import com.example.school.common.dto.ClassLevelDto;
import com.example.school.domain.entities.ClassLevel;

public class ClassLevelMapper {

    public static ClassLevelDto toDto(ClassLevel entity) {
        if (entity == null) return null;

        ClassLevelDto dto = new ClassLevelDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }

    public static ClassLevel toEntity(ClassLevelDto dto) {
        if (dto == null) return null;

        ClassLevel entity = new ClassLevel();
        entity.setId(dto.getId());
        entity.setName(dto.getName());

        return entity;
    }
}
