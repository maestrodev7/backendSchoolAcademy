package com.example.school.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class PaymentStatusDto {
    private UUID registrationId;
    private boolean confirmed;
    private BigDecimal totalExpected;
    private BigDecimal totalPaid;
    private BigDecimal totalRemaining;
    private List<PaymentStatusItemDto> items;
}

