package com.example.school.common.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ClassroomDto {
    private UUID id;
    private String label;
    private UUID classLevelId;
    private UUID seriesId;
    private UUID schoolId;
    private UUID academicYearId;
}
