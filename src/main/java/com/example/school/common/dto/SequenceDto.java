package com.example.school.common.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class SequenceDto {
    private UUID id;
    private String name;
    private Integer number;
    private UUID termId;
    private UUID schoolId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
}

