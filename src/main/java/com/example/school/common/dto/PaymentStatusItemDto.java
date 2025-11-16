package com.example.school.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentStatusItemDto {
    private UUID classFeeId;
    private String feeName;
    private String trancheLabel;
    private Integer trancheOrder;
    private BigDecimal expectedAmount;
    private BigDecimal paidAmount;
    private BigDecimal remainingAmount;
    private boolean fullyPaid;
}


