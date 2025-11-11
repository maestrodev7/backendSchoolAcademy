package com.example.school.domain.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class SchoolFeeConfig {
    private UUID id;
    private School school;
    private ClassRoom classRoom;
    private SchoolFeeType schoolFeeType;
    private BigDecimal amount;
    private int numberOfInstallments;
}
