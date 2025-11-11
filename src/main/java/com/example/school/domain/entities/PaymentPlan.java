package com.example.school.domain.entities;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class PaymentPlan {
    private UUID id;
    private String label;
    private LocalDate dueDate;
    private int orderIndex;
    private School school;
}

