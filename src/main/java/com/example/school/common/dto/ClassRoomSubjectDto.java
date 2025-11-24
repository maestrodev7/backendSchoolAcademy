package com.example.school.common.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ClassRoomSubjectDto {
    private UUID id;
    private UUID subjectId;
    private String subjectName;
    private String subjectCode;
    private UUID classRoomId;
    private String classRoomLabel;
    private Double coefficient;
}

