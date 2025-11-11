package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.SchoolFeeType;
import com.example.school.infrastructure.models.SchoolFeeTypeModel;

public final class SchoolFeeTypeMapper {

    private SchoolFeeTypeMapper() {
    }

    public static SchoolFeeType toDomain(SchoolFeeTypeModel model) {
        if (model == null) {
            return null;
        }
        SchoolFeeType domain = new SchoolFeeType();
        domain.setId(model.getId());
        domain.setLabel(model.getLabel());
        domain.setMandatory(model.isMandatory());
        domain.setSchool(SchoolMapper.toDomain(model.getSchool()));
        return domain;
    }

    public static SchoolFeeTypeModel toModel(SchoolFeeType domain) {
        if (domain == null) {
            return null;
        }
        SchoolFeeTypeModel model = new SchoolFeeTypeModel();
        model.setId(domain.getId());
        model.setLabel(domain.getLabel());
        model.setMandatory(domain.isMandatory());
        model.setSchool(SchoolMapper.toModel(domain.getSchool()));
        return model;
    }
}
