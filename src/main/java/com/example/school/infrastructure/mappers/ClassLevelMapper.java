package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.ClassLevel;
import com.example.school.infrastructure.models.ClassLevelModel;

public class ClassLevelMapper {

    public static ClassLevel toDomain(ClassLevelModel model) {
        if (model == null) {
            return null;
        }

        ClassLevel classLevel = new ClassLevel();
        classLevel.setId(model.getId());
        classLevel.setName(model.getName());

        return classLevel;
    }

    public static ClassLevelModel toModel(ClassLevel entity) {
        if (entity == null) {
            return null;
        }

        ClassLevelModel model = new ClassLevelModel();
        model.setId(entity.getId());
        model.setName(entity.getName());

        return model;
    }
}
