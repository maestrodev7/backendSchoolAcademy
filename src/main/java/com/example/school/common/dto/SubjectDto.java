package com.example.school.common.dto;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class SubjectDto {
    private UUID id;
    private String code;
    private String name;
    private String description;
    private List<ClassRoomSubjectDto> classRoomSubjects;
}

