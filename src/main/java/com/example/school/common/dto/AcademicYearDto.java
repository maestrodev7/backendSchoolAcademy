package com.example.school.common.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class AcademicYearDto {
    private UUID id;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
}
