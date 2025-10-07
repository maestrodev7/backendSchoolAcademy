package com.example.school.domain.entities;

import lombok.Data;

import java.util.UUID;
@Data
public class UserSchool {
    private UUID id;
    private UUID userId;
    private UUID schoolId;
    private String role;
}

