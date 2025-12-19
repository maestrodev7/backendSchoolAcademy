package com.example.school.common.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CompetenceDto {
    private UUID id;
    private UUID subjectId;
    private String subjectName;
    private String description;
    private Integer orderNumber;
}

