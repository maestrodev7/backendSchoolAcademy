package com.example.school.domain.services;

import com.example.school.common.dto.ClassFeeDto;
import com.example.school.common.dto.FeeTypeDto;
import com.example.school.common.dto.PaymentPlanDto;
import com.example.school.presenation.validators.ClassFeeRequestValidator;
import com.example.school.presenation.validators.FeeTypeRequestValidator;
import com.example.school.presenation.validators.PaymentPlanRequestValidator;

import java.util.List;
import java.util.UUID;

public interface FeeManagementServiceInterface {
    FeeTypeDto createFeeType(UUID schoolId, FeeTypeRequestValidator request);

    List<FeeTypeDto> getFeeTypes(UUID schoolId);

    PaymentPlanDto createPaymentPlan(UUID schoolId, PaymentPlanRequestValidator request);

    List<PaymentPlanDto> getPaymentPlans(UUID schoolId);

    ClassFeeDto createClassFee(UUID schoolId, ClassFeeRequestValidator request);

    List<ClassFeeDto> getClassFeesByClass(UUID classRoomId);

    List<ClassFeeDto> getClassFeesBySchool(UUID schoolId);
}

