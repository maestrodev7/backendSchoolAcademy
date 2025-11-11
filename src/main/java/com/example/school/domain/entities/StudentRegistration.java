package com.example.school.domain.entities;

import lombok.Data;

import java.util.UUID;

@Data
public class StudentRegistration {
    private UUID id;
    private User student;
    private ClassRoom classRoom;
    private AcademicYear academicYear;
    private boolean confirmed;
}
