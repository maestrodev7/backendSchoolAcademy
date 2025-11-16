package com.example.school.application.services;

import com.example.school.common.dto.ClassFeeDto;
import com.example.school.common.dto.FeeTypeDto;
import com.example.school.common.dto.PaymentPlanDto;
import com.example.school.common.exceptions.BusinessRuleException;
import com.example.school.common.mapper.ClassFeeDtoMapper;
import com.example.school.common.mapper.FeeTypeDtoMapper;
import com.example.school.common.mapper.PaymentPlanDtoMapper;
import com.example.school.domain.entities.ClassFee;
import com.example.school.domain.entities.FeeType;
import com.example.school.domain.entities.PaymentPlan;
import com.example.school.domain.entities.School;
import com.example.school.domain.repositories.ClassFeeRepositoryInterface;
import com.example.school.domain.repositories.ClassRoomRepositoryInterface;
import com.example.school.domain.repositories.FeeTypeRepositoryInterface;
import com.example.school.domain.repositories.PaymentPlanRepositoryInterface;
import com.example.school.domain.repositories.SchoolRepositoryInterface;
import com.example.school.domain.services.FeeManagementServiceInterface;
import com.example.school.presenation.validators.ClassFeeRequestValidator;
import com.example.school.presenation.validators.FeeTypeRequestValidator;
import com.example.school.presenation.validators.PaymentPlanRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeeManagementService implements FeeManagementServiceInterface {

    private final FeeTypeRepositoryInterface feeTypeRepository;
    private final PaymentPlanRepositoryInterface paymentPlanRepository;
    private final ClassFeeRepositoryInterface classFeeRepository;
    private final SchoolRepositoryInterface schoolRepository;
    private final ClassRoomRepositoryInterface classRoomRepository;

    @Override
    public FeeTypeDto createFeeType(UUID schoolId, FeeTypeRequestValidator request) {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new EntityNotFoundException("École introuvable"));

        FeeType feeType = new FeeType();
        feeType.setName(request.getName());
        feeType.setMandatory(request.isMandatory());
        feeType.setSchool(school);

        FeeType saved = feeTypeRepository.save(feeType);
        return FeeTypeDtoMapper.toDto(saved);
    }

    @Override
    public List<FeeTypeDto> getFeeTypes(UUID schoolId) {
        return feeTypeRepository.findBySchool(schoolId)
                .stream()
                .map(FeeTypeDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentPlanDto createPaymentPlan(UUID schoolId, PaymentPlanRequestValidator request) {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new EntityNotFoundException("École introuvable"));

        PaymentPlan plan = new PaymentPlan();
        plan.setLabel(request.getLabel());
        plan.setDueDate(request.getDueDate());
        plan.setOrderIndex(request.getOrderIndex());
        plan.setSchool(school);

        PaymentPlan saved = paymentPlanRepository.save(plan);
        return PaymentPlanDtoMapper.toDto(saved);
    }

    @Override
    public List<PaymentPlanDto> getPaymentPlans(UUID schoolId) {
        return paymentPlanRepository.findBySchool(schoolId)
                .stream()
                .map(PaymentPlanDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClassFeeDto createClassFee(UUID schoolId, ClassFeeRequestValidator request) {
        var classRoom = classRoomRepository.findById(request.getClassRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Classe introuvable"));

        if (classRoom.getSchool() == null || !schoolId.equals(classRoom.getSchool().getId())) {
            throw new BusinessRuleException("La classe ne correspond pas à l'école ciblée");
        }

        FeeType feeType = feeTypeRepository.findById(request.getFeeTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Type de frais introuvable"));

        if (feeType.getSchool() == null || !schoolId.equals(feeType.getSchool().getId())) {
            throw new BusinessRuleException("Le type de frais ne correspond pas à l'école ciblée");
        }

        PaymentPlan paymentPlan = null;
        if (request.getPaymentPlanId() != null) {
            paymentPlan = paymentPlanRepository.findById(request.getPaymentPlanId())
                    .orElseThrow(() -> new EntityNotFoundException("Plan de paiement introuvable"));
            if (paymentPlan.getSchool() == null || !schoolId.equals(paymentPlan.getSchool().getId())) {
                throw new BusinessRuleException("Le plan de paiement ne correspond pas à l'école ciblée");
            }
        }

        ClassFee classFee = new ClassFee();
        classFee.setClassRoom(classRoom);
        classFee.setFeeType(feeType);
        classFee.setPaymentPlan(paymentPlan);
        classFee.setAmount(request.getAmount());
        classFee.setDueDate(request.getDueDate());

        ClassFee saved = classFeeRepository.save(classFee);
        return ClassFeeDtoMapper.toDto(saved);
    }

    @Override
    public List<ClassFeeDto> getClassFeesByClass(UUID classRoomId) {
        classRoomRepository.findById(classRoomId)
                .orElseThrow(() -> new EntityNotFoundException("Classe introuvable"));

        return classFeeRepository.findByClassRoom(classRoomId)
                .stream()
                .map(ClassFeeDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassFeeDto> getClassFeesBySchool(UUID schoolId, UUID classRoomId) {
        schoolRepository.findById(schoolId)
                .orElseThrow(() -> new EntityNotFoundException("École introuvable"));

        List<ClassFee> classFees = (classRoomId != null)
                ? classFeeRepository.findByClassRoom(classRoomId)
                : classFeeRepository.findBySchool(schoolId);

        return classFees.stream()
                .map(ClassFeeDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}

