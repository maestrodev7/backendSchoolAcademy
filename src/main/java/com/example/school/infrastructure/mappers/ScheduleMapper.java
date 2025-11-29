package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.Schedule;
import com.example.school.infrastructure.models.ScheduleModel;

public class ScheduleMapper {

    public static Schedule toDomain(ScheduleModel model) {
        if (model == null) return null;
        
        Schedule domain = new Schedule();
        domain.setId(model.getId());
        domain.setClassRoomId(model.getClassRoomId());
        domain.setClassRoomSubjectId(model.getClassRoomSubjectId());
        domain.setTeacherSubjectId(model.getTeacherSubjectId());
        domain.setSchoolId(model.getSchoolId());
        domain.setAcademicYearId(model.getAcademicYearId());
        domain.setDayOfWeek(model.getDayOfWeek());
        domain.setStartTime(model.getStartTime());
        domain.setEndTime(model.getEndTime());
        domain.setRoom(model.getRoom());
        domain.setNotes(model.getNotes());
        
        return domain;
    }

    public static ScheduleModel toModel(Schedule domain) {
        if (domain == null) return null;
        
        ScheduleModel model = new ScheduleModel();
        model.setId(domain.getId());
        model.setClassRoomId(domain.getClassRoomId());
        model.setClassRoomSubjectId(domain.getClassRoomSubjectId());
        model.setTeacherSubjectId(domain.getTeacherSubjectId());
        model.setSchoolId(domain.getSchoolId());
        model.setAcademicYearId(domain.getAcademicYearId());
        model.setDayOfWeek(domain.getDayOfWeek());
        model.setStartTime(domain.getStartTime());
        model.setEndTime(domain.getEndTime());
        model.setRoom(domain.getRoom());
        model.setNotes(domain.getNotes());
        
        return model;
    }
}

