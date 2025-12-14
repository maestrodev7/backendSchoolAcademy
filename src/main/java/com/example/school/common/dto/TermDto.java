package com.example.school.common.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class TermDto {
    private UUID id;
    private String name;
    private Integer number;
    private UUID academicYearId;
    private UUID schoolId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
    private List<SequenceDto> sequences;
}

