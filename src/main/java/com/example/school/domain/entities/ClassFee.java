package com.example.school.domain.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ClassFee {
    private UUID id;
    private ClassRoom classRoom;
    private FeeType feeType;
    private PaymentPlan paymentPlan;
    private BigDecimal amount;
    private LocalDate dueDate;
}

