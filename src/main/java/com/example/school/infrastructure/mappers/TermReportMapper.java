package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.TermReport;
import com.example.school.infrastructure.models.TermReportModel;

public class TermReportMapper {
    public static TermReport toDomain(TermReportModel model) {
        if (model == null) return null;
        TermReport report = new TermReport();
        report.setId(model.getId());
        report.setStudent(model.getStudent() != null ? UserMapper.toDomain(model.getStudent()) : null);
        report.setTerm(model.getTerm() != null ? TermMapper.toDomain(model.getTerm()) : null);
        report.setClassRoom(model.getClassRoom() != null ? ClassRoomMapper.toDomain(model.getClassRoom()) : null);
        report.setAcademicYear(model.getAcademicYear() != null ? AcademicYearMapper.toDomain(model.getAcademicYear()) : null);
        report.setTotalGeneral(model.getTotalGeneral());
        report.setTotalCoefficient(model.getTotalCoefficient());
        report.setTermAverage(model.getTermAverage());
        report.setCote(model.getCote());
        report.setStudentWorkAppreciation(model.getStudentWorkAppreciation());
        report.setClassAverage(model.getClassAverage());
        report.setClassMin(model.getClassMin());
        report.setClassMax(model.getClassMax());
        report.setNumberOfAverages(model.getNumberOfAverages());
        report.setSuccessRate(model.getSuccessRate());
        report.setParentVisa(model.getParentVisa());
        report.setPrincipalTeacherVisa(model.getPrincipalTeacherVisa());
        report.setPrincipalTeacher(model.getPrincipalTeacher() != null ? UserMapper.toDomain(model.getPrincipalTeacher()) : null);
        report.setDisciplineRecord(model.getDisciplineRecord() != null ? DisciplineRecordMapper.toDomain(model.getDisciplineRecord()) : null);
        return report;
    }

    public static TermReportModel toModel(TermReport report) {
        if (report == null) return null;
        TermReportModel model = new TermReportModel();
        model.setId(report.getId());
        model.setStudent(report.getStudent() != null ? UserMapper.toModel(report.getStudent()) : null);
        model.setTerm(report.getTerm() != null ? TermMapper.toModel(report.getTerm()) : null);
        model.setClassRoom(report.getClassRoom() != null ? ClassRoomMapper.toModel(report.getClassRoom()) : null);
        model.setAcademicYear(report.getAcademicYear() != null ? AcademicYearMapper.toModel(report.getAcademicYear()) : null);
        model.setTotalGeneral(report.getTotalGeneral());
        model.setTotalCoefficient(report.getTotalCoefficient());
        model.setTermAverage(report.getTermAverage());
        model.setCote(report.getCote());
        model.setStudentWorkAppreciation(report.getStudentWorkAppreciation());
        model.setClassAverage(report.getClassAverage());
        model.setClassMin(report.getClassMin());
        model.setClassMax(report.getClassMax());
        model.setNumberOfAverages(report.getNumberOfAverages());
        model.setSuccessRate(report.getSuccessRate());
        model.setParentVisa(report.getParentVisa());
        model.setPrincipalTeacherVisa(report.getPrincipalTeacherVisa());
        model.setPrincipalTeacher(report.getPrincipalTeacher() != null ? UserMapper.toModel(report.getPrincipalTeacher()) : null);
        model.setDisciplineRecord(report.getDisciplineRecord() != null ? DisciplineRecordMapper.toModel(report.getDisciplineRecord()) : null);
        return model;
    }
}

