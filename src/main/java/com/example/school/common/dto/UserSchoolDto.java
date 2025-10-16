package com.example.school.common.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserSchoolDto {
    private UUID id;
    private UUID userId;
    private UUID schoolId;
    private String role;

    private UserDto user;     // 👈 ajouté
    private SchoolDto school;// ex: ADMIN, TEACHER, STUDENT, PARENT
}
