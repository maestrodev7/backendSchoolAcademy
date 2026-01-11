package com.example.school.common.mapper;

import com.example.school.common.dto.AbsenceDto;
import com.example.school.domain.entities.Absence;

public class AbsenceMapper {
    
    public static AbsenceDto toDto(Absence absence) {
        if (absence == null) return null;
        
        AbsenceDto dto = new AbsenceDto();
        dto.setId(absence.getId());
        
        if (absence.getStudent() != null) {
            dto.setStudentId(absence.getStudent().getId());
            dto.setStudentName((absence.getStudent().getFirstName() + " " + absence.getStudent().getLastName()).trim());
        }
        
        if (absence.getClassRoom() != null) {
            dto.setClassRoomId(absence.getClassRoom().getId());
            dto.setClassRoomLabel(absence.getClassRoom().getLabel());
        }
        
        if (absence.getSchool() != null) {
            dto.setSchoolId(absence.getSchool().getId());
            dto.setSchoolName(absence.getSchool().getName());
        }
        
        if (absence.getSubject() != null) {
            dto.setSubjectId(absence.getSubject().getId());
            dto.setSubjectName(absence.getSubject().getName());
            dto.setSubjectCode(absence.getSubject().getCode());
        }
        
        if (absence.getAcademicYear() != null) {
            dto.setAcademicYearId(absence.getAcademicYear().getId());
            if (absence.getAcademicYear().getStartDate() != null && absence.getAcademicYear().getEndDate() != null) {
                dto.setAcademicYearLabel(absence.getAcademicYear().getStartDate() + " - " + absence.getAcademicYear().getEndDate());
            } else {
                dto.setAcademicYearLabel(null);
            }
        }
        
        dto.setScheduleId(absence.getScheduleId());
        dto.setDate(absence.getDate());
        dto.setNumberOfHours(absence.getNumberOfHours());
        dto.setNotes(absence.getNotes());
        
        return dto;
    }
}
