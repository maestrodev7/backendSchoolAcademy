package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.SchoolFeeConfig;
import com.example.school.infrastructure.models.SchoolFeeConfigModel;

public final class SchoolFeeConfigMapper {

    private SchoolFeeConfigMapper() {
    }

    public static SchoolFeeConfig toDomain(SchoolFeeConfigModel model) {
        if (model == null) {
            return null;
        }
        SchoolFeeConfig config = new SchoolFeeConfig();
        config.setId(model.getId());
        config.setSchool(SchoolMapper.toDomain(model.getSchool()));
        config.setClassRoom(ClassRoomMapper.toDomain(model.getClassRoom()));
        config.setSchoolFeeType(SchoolFeeTypeMapper.toDomain(model.getSchoolFeeType()));
        config.setAmount(model.getAmount());
        config.setNumberOfInstallments(model.getNumberOfInstallments());
        return config;
    }

    public static SchoolFeeConfigModel toModel(SchoolFeeConfig config) {
        if (config == null) {
            return null;
        }
        SchoolFeeConfigModel model = new SchoolFeeConfigModel();
        model.setId(config.getId());
        model.setSchool(SchoolMapper.toModel(config.getSchool()));
        model.setClassRoom(ClassRoomMapper.toModel(config.getClassRoom()));
        model.setSchoolFeeType(SchoolFeeTypeMapper.toModel(config.getSchoolFeeType()));
        model.setAmount(config.getAmount());
        model.setNumberOfInstallments(config.getNumberOfInstallments());
        return model;
    }
}
