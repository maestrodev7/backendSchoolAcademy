package com.example.school.common.mapper;

import com.example.school.common.dto.SeriesDto;
import com.example.school.domain.entities.Series;

public class SeriesMapper {

    public static SeriesDto toDto(Series entity) {
        if (entity == null) return null;

        SeriesDto dto = new SeriesDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());

        return dto;
    }

    public static Series toEntity(SeriesDto dto) {
        if (dto == null) return null;

        Series entity = new Series();
        entity.setId(dto.getId());
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());

        return entity;
    }
}
