package com.example.school.common.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class StudentRegistrationDto {
    private UUID id;
    private UUID studentId;
    private String studentFullName;
    private UUID classRoomId;
    private String classLabel;
    private UUID academicYearId;
    private boolean confirmed;
}

