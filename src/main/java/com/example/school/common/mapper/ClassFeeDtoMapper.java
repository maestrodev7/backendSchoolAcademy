package com.example.school.common.mapper;

import com.example.school.common.dto.ClassFeeDto;
import com.example.school.domain.entities.ClassFee;

public final class ClassFeeDtoMapper {

    private ClassFeeDtoMapper() {
    }

    public static ClassFeeDto toDto(ClassFee classFee) {
        if (classFee == null) {
            return null;
        }
        ClassFeeDto dto = new ClassFeeDto();
        dto.setId(classFee.getId());
        if (classFee.getClassRoom() != null) {
            dto.setClassRoomId(classFee.getClassRoom().getId());
            dto.setClassLabel(classFee.getClassRoom().getLabel());
        }
        if (classFee.getFeeType() != null) {
            dto.setFeeTypeId(classFee.getFeeType().getId());
            dto.setFeeName(classFee.getFeeType().getName());
            dto.setMandatory(classFee.getFeeType().isMandatory());
        }
        if (classFee.getPaymentPlan() != null) {
            dto.setPaymentPlanId(classFee.getPaymentPlan().getId());
            dto.setPaymentPlanLabel(classFee.getPaymentPlan().getLabel());
            dto.setPaymentPlanOrder(classFee.getPaymentPlan().getOrderIndex());
        }
        dto.setAmount(classFee.getAmount());
        dto.setDueDate(classFee.getDueDate());
        return dto;
    }
}

