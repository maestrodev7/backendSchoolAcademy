package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.PaymentPlan;
import com.example.school.domain.repositories.PaymentPlanRepositoryInterface;
import com.example.school.infrastructure.mappers.PaymentPlanMapper;
import com.example.school.infrastructure.models.PaymentPlanModel;
import com.example.school.infrastructure.repositories.jpa.JpaPaymentPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PaymentPlanRepositoryImpl implements PaymentPlanRepositoryInterface {

    private final JpaPaymentPlanRepository jpaPaymentPlanRepository;

    @Override
    public PaymentPlan save(PaymentPlan paymentPlan) {
        PaymentPlanModel model = PaymentPlanMapper.toModel(paymentPlan);
        PaymentPlanModel saved = jpaPaymentPlanRepository.save(model);
        return PaymentPlanMapper.toDomain(saved);
    }

    @Override
    public Optional<PaymentPlan> findById(UUID id) {
        return jpaPaymentPlanRepository.findById(id).map(PaymentPlanMapper::toDomain);
    }

    @Override
    public List<PaymentPlan> findBySchool(UUID schoolId) {
        return jpaPaymentPlanRepository.findBySchool_Id(schoolId)
                .stream()
                .map(PaymentPlanMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaPaymentPlanRepository.deleteById(id);
    }
}

