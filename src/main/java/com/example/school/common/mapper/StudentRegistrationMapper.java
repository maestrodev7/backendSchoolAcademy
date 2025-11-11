package com.example.school.common.mapper;

import com.example.school.common.dto.StudentRegistrationDto;
import com.example.school.domain.entities.StudentRegistration;

public final class StudentRegistrationMapper {

    private StudentRegistrationMapper() {
    }

    public static StudentRegistrationDto toDto(StudentRegistration registration) {
        if (registration == null) {
            return null;
        }
        StudentRegistrationDto dto = new StudentRegistrationDto();
        dto.setId(registration.getId());
        if (registration.getStudent() != null) {
            dto.setStudentId(registration.getStudent().getId());
            String firstName = registration.getStudent().getFirstName();
            String lastName = registration.getStudent().getLastName();
            dto.setStudentFullName(String.format("%s %s",
                    lastName != null ? lastName : "",
                    firstName != null ? firstName : "").trim());
        }
        if (registration.getClassRoom() != null) {
            dto.setClassRoomId(registration.getClassRoom().getId());
            dto.setClassLabel(registration.getClassRoom().getLabel());
        }
        if (registration.getAcademicYear() != null) {
            dto.setAcademicYearId(registration.getAcademicYear().getId());
        }
        dto.setConfirmed(registration.isConfirmed());
        return dto;
    }
}

