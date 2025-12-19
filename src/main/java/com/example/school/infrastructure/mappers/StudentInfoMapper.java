package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.StudentInfo;
import com.example.school.infrastructure.models.StudentInfoModel;

public class StudentInfoMapper {
    public static StudentInfo toDomain(StudentInfoModel model) {
        if (model == null) return null;
        StudentInfo info = new StudentInfo();
        info.setId(model.getId());
        info.setStudent(model.getStudent() != null ? UserMapper.toDomain(model.getStudent()) : null);
        info.setUniqueIdentifier(model.getUniqueIdentifier());
        info.setBirthDate(model.getBirthDate());
        info.setBirthPlace(model.getBirthPlace());
        info.setGender(model.getGender());
        info.setRepeating(model.isRepeating());
        info.setParentNames(model.getParentNames());
        info.setParentContacts(model.getParentContacts());
        info.setPhotoUrl(model.getPhotoUrl());
        return info;
    }

    public static StudentInfoModel toModel(StudentInfo info) {
        if (info == null) return null;
        StudentInfoModel model = new StudentInfoModel();
        model.setId(info.getId());
        model.setStudent(info.getStudent() != null ? UserMapper.toModel(info.getStudent()) : null);
        model.setUniqueIdentifier(info.getUniqueIdentifier());
        model.setBirthDate(info.getBirthDate());
        model.setBirthPlace(info.getBirthPlace());
        model.setGender(info.getGender());
        model.setRepeating(info.isRepeating());
        model.setParentNames(info.getParentNames());
        model.setParentContacts(info.getParentContacts());
        model.setPhotoUrl(info.getPhotoUrl());
        return model;
    }
}

