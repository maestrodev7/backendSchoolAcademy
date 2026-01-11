package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.Absence;
import com.example.school.domain.entities.Subject;
import com.example.school.infrastructure.models.AbsenceModel;

public class AbsenceMapper {
    
    public static Absence toDomain(AbsenceModel model) {
        if (model == null) return null;
        
        Absence absence = new Absence();
        absence.setId(model.getId());
        absence.setStudent(model.getStudent() != null ? UserMapper.toDomain(model.getStudent()) : null);
        absence.setClassRoom(model.getClassRoom() != null ? ClassRoomMapper.toDomain(model.getClassRoom()) : null);
        absence.setSchool(model.getSchool() != null ? SchoolMapper.toDomain(model.getSchool()) : null);
        
        // Mapper un Subject "léger" sans ses collections pour éviter
        // les ConcurrentModificationException et le chargement en cascade.
        if (model.getSubject() != null) {
            Subject subject = new Subject();
            subject.setId(model.getSubject().getId());
            subject.setCode(model.getSubject().getCode());
            subject.setName(model.getSubject().getName());
            subject.setDescription(model.getSubject().getDescription());
            // Ne pas mapper classRoomSubjects pour éviter la récursion et les problèmes de lazy loading
            absence.setSubject(subject);
        } else {
            absence.setSubject(null);
        }
        
        absence.setAcademicYear(model.getAcademicYear() != null ? AcademicYearMapper.toDomain(model.getAcademicYear()) : null);
        absence.setScheduleId(model.getScheduleId());
        absence.setDate(model.getDate());
        absence.setNumberOfHours(model.getNumberOfHours());
        absence.setNotes(model.getNotes());
        
        return absence;
    }
    
    public static AbsenceModel toModel(Absence absence) {
        if (absence == null) return null;
        
        AbsenceModel model = new AbsenceModel();
        model.setId(absence.getId());
        model.setStudent(absence.getStudent() != null ? UserMapper.toModel(absence.getStudent()) : null);
        model.setClassRoom(absence.getClassRoom() != null ? ClassRoomMapper.toModel(absence.getClassRoom()) : null);
        model.setSchool(absence.getSchool() != null ? SchoolMapper.toModel(absence.getSchool()) : null);
        model.setSubject(absence.getSubject() != null ? SubjectMapper.toModel(absence.getSubject()) : null);
        model.setAcademicYear(absence.getAcademicYear() != null ? AcademicYearMapper.toModel(absence.getAcademicYear()) : null);
        model.setScheduleId(absence.getScheduleId());
        model.setDate(absence.getDate());
        model.setNumberOfHours(absence.getNumberOfHours());
        model.setNotes(absence.getNotes());
        
        return model;
    }
}
