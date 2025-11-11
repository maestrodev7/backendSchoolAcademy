package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.ClassFeeDto;
import com.example.school.common.dto.FeeTypeDto;
import com.example.school.common.dto.PaymentPlanDto;
import com.example.school.domain.services.FeeManagementServiceInterface;
import com.example.school.presenation.validators.ClassFeeRequestValidator;
import com.example.school.presenation.validators.FeeTypeRequestValidator;
import com.example.school.presenation.validators.PaymentPlanRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/schools/{schoolId}")
@RequiredArgsConstructor
public class SchoolFeeController {

    private final FeeManagementServiceInterface feeManagementService;

    @PostMapping("/fee-types")
    public ResponseEntity<ApiResponse<FeeTypeDto>> createFeeType(
            @PathVariable UUID schoolId,
            @Valid @RequestBody FeeTypeRequestValidator request) {
        FeeTypeDto dto = feeManagementService.createFeeType(schoolId, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Type de frais créé avec succès", dto));
    }

    @GetMapping("/fee-types")
    public ResponseEntity<ApiResponse<List<FeeTypeDto>>> getFeeTypes(@PathVariable UUID schoolId) {
        List<FeeTypeDto> data = feeManagementService.getFeeTypes(schoolId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Types de frais récupérés avec succès", data));
    }

    @PostMapping("/payment-plans")
    public ResponseEntity<ApiResponse<PaymentPlanDto>> createPaymentPlan(
            @PathVariable UUID schoolId,
            @Valid @RequestBody PaymentPlanRequestValidator request) {
        PaymentPlanDto dto = feeManagementService.createPaymentPlan(schoolId, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Plan de paiement créé avec succès", dto));
    }

    @GetMapping("/payment-plans")
    public ResponseEntity<ApiResponse<List<PaymentPlanDto>>> getPaymentPlans(@PathVariable UUID schoolId) {
        List<PaymentPlanDto> data = feeManagementService.getPaymentPlans(schoolId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Plans de paiement récupérés avec succès", data));
    }

    @PostMapping("/class-fees")
    public ResponseEntity<ApiResponse<ClassFeeDto>> createClassFee(
            @PathVariable UUID schoolId,
            @Valid @RequestBody ClassFeeRequestValidator request) {
        ClassFeeDto dto = feeManagementService.createClassFee(schoolId, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Frais de classe créé avec succès", dto));
    }

    @GetMapping("/class-fees/{classRoomId}")
    public ResponseEntity<ApiResponse<List<ClassFeeDto>>> getClassFees(
            @PathVariable UUID schoolId,
            @PathVariable UUID classRoomId) {
        List<ClassFeeDto> data = feeManagementService.getClassFeesByClass(classRoomId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Frais de la classe récupérés avec succès", data));
    }
}

