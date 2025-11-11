package com.example.school.common.mapper;

import com.example.school.common.dto.PaymentDto;
import com.example.school.domain.entities.Payment;

public final class PaymentDtoMapper {

    private PaymentDtoMapper() {
    }

    public static PaymentDto toDto(Payment payment) {
        if (payment == null) {
            return null;
        }
        PaymentDto dto = new PaymentDto();
        dto.setId(payment.getId());
        if (payment.getRegistration() != null) {
            dto.setRegistrationId(payment.getRegistration().getId());
        }
        if (payment.getClassFee() != null) {
            dto.setClassFeeId(payment.getClassFee().getId());
            if (payment.getClassFee().getFeeType() != null) {
                dto.setFeeName(payment.getClassFee().getFeeType().getName());
            }
            if (payment.getClassFee().getPaymentPlan() != null) {
                dto.setTrancheLabel(payment.getClassFee().getPaymentPlan().getLabel());
                dto.setTrancheOrder(payment.getClassFee().getPaymentPlan().getOrderIndex());
            }
        }
        dto.setAmountPaid(payment.getAmountPaid());
        dto.setPaymentDate(payment.getPaymentDate());
        return dto;
    }
}

