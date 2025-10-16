package com.example.school.domain.entities;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
public class ClassRoom {
    private UUID id;

    private String label;

    private School school;
    private AcademicYear academicYear;
    private ClassLevel classLevel;
    private Series series;
}
