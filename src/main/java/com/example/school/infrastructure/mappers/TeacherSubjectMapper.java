package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.TeacherSubject;
import com.example.school.infrastructure.models.TeacherSubjectModel;

public class TeacherSubjectMapper {

    public static TeacherSubject toDomain(TeacherSubjectModel model) {
        if (model == null) return null;
        
        TeacherSubject domain = new TeacherSubject();
        domain.setId(model.getId());
        domain.setUserSchoolId(model.getUserSchoolId());
        domain.setSubjectId(model.getSubjectId());
        domain.setSchoolId(model.getSchoolId());
        domain.setAcademicYearId(model.getAcademicYearId());
        domain.setSpecialization(model.getSpecialization());
        domain.setExperienceYears(model.getExperienceYears());
        
        return domain;
    }

    public static TeacherSubjectModel toModel(TeacherSubject domain) {
        if (domain == null) return null;
        
        TeacherSubjectModel model = new TeacherSubjectModel();
        model.setId(domain.getId());
        model.setUserSchoolId(domain.getUserSchoolId());
        model.setSubjectId(domain.getSubjectId());
        model.setSchoolId(domain.getSchoolId());
        model.setAcademicYearId(domain.getAcademicYearId());
        model.setSpecialization(domain.getSpecialization());
        model.setExperienceYears(domain.getExperienceYears());
        
        return model;
    }
}

