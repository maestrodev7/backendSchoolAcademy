package com.example.school.domain.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class Payment {
    private UUID id;
    private StudentRegistration registration;
    private ClassFee classFee;
    private BigDecimal amountPaid;
    private LocalDate paymentDate;
}
