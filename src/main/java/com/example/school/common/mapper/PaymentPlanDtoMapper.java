package com.example.school.common.mapper;

import com.example.school.common.dto.PaymentPlanDto;
import com.example.school.domain.entities.PaymentPlan;

public final class PaymentPlanDtoMapper {

    private PaymentPlanDtoMapper() {
    }

    public static PaymentPlanDto toDto(PaymentPlan paymentPlan) {
        if (paymentPlan == null) {
            return null;
        }
        PaymentPlanDto dto = new PaymentPlanDto();
        dto.setId(paymentPlan.getId());
        dto.setLabel(paymentPlan.getLabel());
        dto.setDueDate(paymentPlan.getDueDate());
        dto.setOrderIndex(paymentPlan.getOrderIndex());
        if (paymentPlan.getSchool() != null) {
            dto.setSchoolId(paymentPlan.getSchool().getId());
        }
        return dto;
    }
}

