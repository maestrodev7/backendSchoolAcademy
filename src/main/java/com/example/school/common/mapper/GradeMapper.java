package com.example.school.common.mapper;

import com.example.school.common.dto.GradeDto;
import com.example.school.domain.entities.Grade;

public class GradeMapper {
    public static GradeDto toDto(Grade grade) {
        if (grade == null) return null;
        GradeDto dto = new GradeDto();
        dto.setId(grade.getId());
        dto.setCompetenceId(grade.getCompetence() != null ? grade.getCompetence().getId() : null);
        dto.setCompetenceDescription(grade.getCompetence() != null ? grade.getCompetence().getDescription() : null);
        dto.setSubjectId(grade.getCompetence() != null && grade.getCompetence().getSubject() != null 
                ? grade.getCompetence().getSubject().getId() : null);
        dto.setSubjectName(grade.getCompetence() != null && grade.getCompetence().getSubject() != null 
                ? grade.getCompetence().getSubject().getName() : null);
        dto.setStudentId(grade.getStudent() != null ? grade.getStudent().getId() : null);
        dto.setStudentName(grade.getStudent() != null 
                ? (grade.getStudent().getFirstName() + " " + grade.getStudent().getLastName()).trim() : null);
        dto.setTermId(grade.getTerm() != null ? grade.getTerm().getId() : null);
        dto.setTermName(grade.getTerm() != null ? grade.getTerm().getName() : null);
        dto.setSequenceId(grade.getSequence() != null ? grade.getSequence().getId() : null);
        dto.setSequenceName(grade.getSequence() != null ? grade.getSequence().getName() : null);
        dto.setNoteN20(grade.getNoteN20());
        dto.setNoteM20(grade.getNoteM20());
        dto.setCoefficient(grade.getCoefficient());
        dto.setMXCoef(grade.getMXCoef());
        dto.setCote(grade.getCote());
        dto.setMinScore(grade.getMinScore());
        dto.setMaxScore(grade.getMaxScore());
        dto.setAppreciation(grade.getAppreciation());
        dto.setTeacherId(grade.getTeacher() != null ? grade.getTeacher().getId() : null);
        dto.setTeacherName(grade.getTeacher() != null 
                ? (grade.getTeacher().getFirstName() + " " + grade.getTeacher().getLastName()).trim() : null);
        return dto;
    }
}

