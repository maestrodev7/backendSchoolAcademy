package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.StudentRegistration;
import com.example.school.infrastructure.models.StudentRegistrationModel;

public final class StudentRegistrationMapper {

    private StudentRegistrationMapper() {
    }

    public static StudentRegistration toDomain(StudentRegistrationModel model) {
        if (model == null) {
            return null;
        }
        StudentRegistration registration = new StudentRegistration();
        registration.setId(model.getId());
        registration.setStudent(UserMapper.toDomain(model.getStudent()));
        registration.setClassRoom(ClassRoomMapper.toDomain(model.getClassRoom()));
        registration.setAcademicYear(AcademicYearMapper.toDomain(model.getAcademicYear()));
        registration.setConfirmed(model.isConfirmed());
        return registration;
    }

    public static StudentRegistrationModel toModel(StudentRegistration registration) {
        if (registration == null) {
            return null;
        }
        StudentRegistrationModel model = new StudentRegistrationModel();
        model.setId(registration.getId());
        model.setStudent(UserMapper.toModel(registration.getStudent()));
        model.setClassRoom(ClassRoomMapper.toModel(registration.getClassRoom()));
        model.setAcademicYear(AcademicYearMapper.toModel(registration.getAcademicYear()));
        model.setConfirmed(registration.isConfirmed());
        return model;
    }
}
