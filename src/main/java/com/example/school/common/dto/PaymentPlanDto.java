package com.example.school.common.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class PaymentPlanDto {
    private UUID id;
    private String label;
    private LocalDate dueDate;
    private int orderIndex;
    private UUID schoolId;
}

