package com.example.school.domain.entities;

import lombok.Data;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

@Data
public class Subject {
    private UUID id;
    private String name;
    private String code;
    private String description;

    @ToString.Exclude
    private Set<ClassRoomSubject> classRoomSubjects;
}

