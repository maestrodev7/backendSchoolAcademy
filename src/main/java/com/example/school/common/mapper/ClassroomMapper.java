package com.example.school.common.mapper;

import com.example.school.common.dto.ClassroomDto;
import com.example.school.domain.entities.ClassLevel;
import com.example.school.domain.entities.ClassRoom;
import com.example.school.domain.entities.Series;

public class ClassroomMapper {

    public static ClassroomDto toDto(ClassRoom entity) {
        if (entity == null) return null;

        ClassroomDto dto = new ClassroomDto();
        dto.setId(entity.getId());
        dto.setLabel(entity.getLabel());

        if (entity.getClassLevel() != null) {
            dto.setClassLevelId(entity.getClassLevel().getId());
        }

        if (entity.getSeries() != null) {
            dto.setSeriesId(entity.getSeries().getId());
        }

        return dto;
    }

    public static ClassRoom toEntity(ClassroomDto dto) {
        if (dto == null) return null;

        ClassRoom entity = new ClassRoom();
        entity.setId(dto.getId());
        entity.setLabel(dto.getLabel()); // ou setLabel(...)

        if (dto.getClassLevelId() != null) {
            ClassLevel cl = new ClassLevel();
            cl.setId(dto.getClassLevelId());
            entity.setClassLevel(cl);
        }

        if (dto.getSeriesId() != null) {
            Series s = new Series();
            s.setId(dto.getSeriesId());
            entity.setSeries(s);
        }

        return entity;
    }
}
