package com.example.school.domain.entities;

import lombok.Data;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

@Data
public class School {
    private UUID id;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;

    @ToString.Exclude
    private Set<AcademicYear> academicYears;

    @ToString.Exclude
    private Set<ClassRoom> classRooms;
}
