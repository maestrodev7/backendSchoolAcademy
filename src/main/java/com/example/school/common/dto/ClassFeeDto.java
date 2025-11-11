package com.example.school.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ClassFeeDto {
    private UUID id;
    private UUID classRoomId;
    private String classLabel;
    private UUID feeTypeId;
    private String feeName;
    private boolean mandatory;
    private UUID paymentPlanId;
    private String paymentPlanLabel;
    private Integer paymentPlanOrder;
    private BigDecimal amount;
    private LocalDate dueDate;
}

