package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.FeeType;
import com.example.school.infrastructure.models.FeeTypeModel;

public final class FeeTypeMapper {

    private FeeTypeMapper() {
    }

    public static FeeType toDomain(FeeTypeModel model) {
        if (model == null) {
            return null;
        }
        FeeType feeType = new FeeType();
        feeType.setId(model.getId());
        feeType.setName(model.getName());
        feeType.setMandatory(model.isMandatory());
        feeType.setSchool(SchoolMapper.toDomain(model.getSchool()));
        return feeType;
    }

    public static FeeTypeModel toModel(FeeType feeType) {
        if (feeType == null) {
            return null;
        }
        FeeTypeModel model = new FeeTypeModel();
        model.setId(feeType.getId());
        model.setName(feeType.getName());
        model.setMandatory(feeType.isMandatory());
        model.setSchool(SchoolMapper.toModel(feeType.getSchool()));
        return model;
    }
}

