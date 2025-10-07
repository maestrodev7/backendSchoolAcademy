package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.School;
import com.example.school.domain.entities.AcademicYear;
import com.example.school.infrastructure.models.SchoolModel;
import com.example.school.infrastructure.models.AcademicYearModel;

import java.util.Set;
import java.util.stream.Collectors;

public class SchoolMapper {

    public static School toDomain(SchoolModel model) {
        if (model == null) return null;

        School school = new School();
        school.setId(model.getId());
        school.setName(model.getName());
        school.setAddress(model.getAddress());
        school.setEmail(model.getEmail());
        school.setPhoneNumber(model.getPhoneNumber());

        // Mapper les années académiques si elles existent
        if (model.getAcademicYears() != null && !model.getAcademicYears().isEmpty()) {
            Set<AcademicYear> years = model.getAcademicYears()
                    .stream()
                    .map(AcademicYearMapper::toDomain)
                    .collect(Collectors.toSet());
            school.setAcademicYears(years);
        }

        return school;
    }

    public static SchoolModel toModel(School school) {
        if (school == null) return null;

        SchoolModel model = new SchoolModel();
        model.setId(school.getId());
        model.setName(school.getName());
        model.setAddress(school.getAddress());
        model.setEmail(school.getEmail());
        model.setPhoneNumber(school.getPhoneNumber());

        // Mapper les années académiques associées
        if (school.getAcademicYears() != null && !school.getAcademicYears().isEmpty()) {
            Set<AcademicYearModel> yearModels = school.getAcademicYears()
                    .stream()
                    .map(AcademicYearMapper::toModel)
                    .collect(Collectors.toSet());
            model.setAcademicYears(yearModels);
        }

        return model;
    }
}
