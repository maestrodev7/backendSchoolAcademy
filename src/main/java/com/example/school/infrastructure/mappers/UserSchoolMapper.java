package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.UserSchool;
import com.example.school.infrastructure.models.UserSchoolModel;

public class UserSchoolMapper {

    public static UserSchool toDomain(UserSchoolModel model) {
        UserSchool userSchool = new UserSchool();
        userSchool.setId(model.getId());
        userSchool.setUserId(model.getUserId());
        userSchool.setSchoolId(model.getSchoolId());
        userSchool.setRole(model.getRole());
        return userSchool;
    }

    public static UserSchoolModel toModel(UserSchool entity) {
        UserSchoolModel model = new UserSchoolModel();
        model.setId(entity.getId());
        model.setUserId(entity.getUserId());
        model.setSchoolId(entity.getSchoolId());
        model.setRole(entity.getRole());
        return model;
    }
}
