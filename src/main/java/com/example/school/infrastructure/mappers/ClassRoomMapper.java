package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.ClassRoom;
import com.example.school.infrastructure.models.ClassRoomModel;

public class ClassRoomMapper {

    public static ClassRoom toDomain(ClassRoomModel model) {
        if (model == null) return null;
        ClassRoom domain = new ClassRoom();
        domain.setId(model.getId());
        domain.setLabel(model.getLabel());
        domain.setSchool(SchoolMapper.toDomain(model.getSchool()));
        domain.setAcademicYear(AcademicYearMapper.toDomain(model.getAcademicYear()));
        domain.setClassLevel(ClassLevelMapper.toDomain(model.getClassLevel()));
        domain.setSeries(SeriesMapper.toDomain(model.getSeries()));
        return domain;
    }

    public static ClassRoomModel toModel(ClassRoom domain) {
        if (domain == null) return null;
        ClassRoomModel model = new ClassRoomModel();
        model.setId(domain.getId());
        model.setLabel(domain.getLabel());
        model.setSchool(SchoolMapper.toModel(domain.getSchool()));
        model.setAcademicYear(AcademicYearMapper.toModel(domain.getAcademicYear()));
        model.setClassLevel(ClassLevelMapper.toModel(domain.getClassLevel()));
        model.setSeries(SeriesMapper.toModel(domain.getSeries()));
        return model;
    }
}
