package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.AcademicYear;
import com.example.school.infrastructure.models.AcademicYearModel;

public class AcademicYearMapper {

    public static AcademicYear toDomain(AcademicYearModel model) {
        if (model == null) return null;
        AcademicYear year = new AcademicYear();
        year.setId(model.getId());
        year.setStartDate(model.getStartDate());
        year.setEndDate(model.getEndDate());
        year.setActive(model.isActive());
        // Ne pas mapper les écoles pour éviter la récursion infinie
        // La validation utilise existsByAcademicYearIdAndSchoolId qui fait une requête directe
        return year;
    }

    public static AcademicYearModel toModel(AcademicYear year) {
        if (year == null) return null;
        AcademicYearModel model = new AcademicYearModel();
        model.setId(year.getId());
        model.setStartDate(year.getStartDate());
        model.setEndDate(year.getEndDate());
        model.setActive(year.isActive());
        return model;
    }
}

