package com.example.school.common.mapper;

import com.example.school.common.dto.AcademicYearDto;
import com.example.school.domain.entities.AcademicYear;
import com.example.school.presenation.validators.AcademicYearRequestValidator;

public class AcademicYearMapper {

    // Convertir de l'entité vers le DTO
    public static AcademicYearDto toDto(AcademicYear entity) {
        if (entity == null) return null;
        AcademicYearDto dto = new AcademicYearDto();
        dto.setId(entity.getId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setActive(entity.isActive());
        return dto;
    }

    public static AcademicYear toEntity(AcademicYearDto dto) {
        if (dto == null) return null;
        AcademicYear entity = new AcademicYear();
        entity.setId(dto.getId());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setActive(dto.isActive());
        return entity;
    }

    public static AcademicYear toEntity(AcademicYearRequestValidator request) {
        if (request == null) return null;
        AcademicYear entity = new AcademicYear();
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        entity.setActive(request.isActive());
        return entity;
    }
}
