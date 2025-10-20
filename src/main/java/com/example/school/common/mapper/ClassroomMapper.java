package com.example.school.common.mapper;

import com.example.school.common.dto.ClassroomDto;
import com.example.school.domain.entities.*;

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
        if (entity.getSchool() != null) {
            dto.setSchoolId(entity.getSchool().getId());
        }

        if (entity.getAcademicYear() != null) {
            dto.setAcademicYearId(entity.getAcademicYear().getId());
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

        if (dto.getSchoolId() != null) {
            School school = new School();
            school.setId(dto.getSchoolId());
            entity.setSchool(school);
        }

        if (dto.getAcademicYearId() != null) {
            AcademicYear ay = new AcademicYear();
            ay.setId(dto.getAcademicYearId());
            entity.setAcademicYear(ay);
        }

        return entity;
    }
}
