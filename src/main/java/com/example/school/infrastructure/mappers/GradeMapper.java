package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.Grade;
import com.example.school.infrastructure.models.GradeModel;

public class GradeMapper {
    public static Grade toDomain(GradeModel model) {
        if (model == null) return null;
        Grade grade = new Grade();
        grade.setId(model.getId());
        grade.setCompetence(model.getCompetence() != null ? CompetenceMapper.toDomain(model.getCompetence()) : null);
        grade.setStudent(model.getStudent() != null ? UserMapper.toDomain(model.getStudent()) : null);
        grade.setTerm(model.getTerm() != null ? TermMapper.toDomain(model.getTerm()) : null);
        grade.setSequence(model.getSequence() != null ? SequenceMapper.toDomain(model.getSequence()) : null);
        grade.setNoteN20(model.getNoteN20());
        grade.setNoteM20(model.getNoteM20());
        grade.setCoefficient(model.getCoefficient());
        grade.setMXCoef(model.getMXCoef());
        grade.setCote(model.getCote());
        grade.setMinScore(model.getMinScore());
        grade.setMaxScore(model.getMaxScore());
        grade.setAppreciation(model.getAppreciation());
        grade.setTeacher(model.getTeacher() != null ? UserMapper.toDomain(model.getTeacher()) : null);
        return grade;
    }

    public static GradeModel toModel(Grade grade) {
        if (grade == null) return null;
        GradeModel model = new GradeModel();
        model.setId(grade.getId());
        model.setCompetence(grade.getCompetence() != null ? CompetenceMapper.toModel(grade.getCompetence()) : null);
        model.setStudent(grade.getStudent() != null ? UserMapper.toModel(grade.getStudent()) : null);
        model.setTerm(grade.getTerm() != null ? TermMapper.toModel(grade.getTerm()) : null);
        model.setSequence(grade.getSequence() != null ? SequenceMapper.toModel(grade.getSequence()) : null);
        model.setNoteN20(grade.getNoteN20());
        model.setNoteM20(grade.getNoteM20());
        model.setCoefficient(grade.getCoefficient());
        model.setMXCoef(grade.getMXCoef());
        model.setCote(grade.getCote());
        model.setMinScore(grade.getMinScore());
        model.setMaxScore(grade.getMaxScore());
        model.setAppreciation(grade.getAppreciation());
        model.setTeacher(grade.getTeacher() != null ? UserMapper.toModel(grade.getTeacher()) : null);
        return model;
    }
}

