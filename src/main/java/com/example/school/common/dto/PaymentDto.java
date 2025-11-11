package com.example.school.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class PaymentDto {
    private UUID id;
    private UUID registrationId;
    private UUID classFeeId;
    private String feeName;
    private String trancheLabel;
    private Integer trancheOrder;
    private BigDecimal amountPaid;
    private LocalDate paymentDate;
}

