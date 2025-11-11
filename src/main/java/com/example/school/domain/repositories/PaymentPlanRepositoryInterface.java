package com.example.school.domain.repositories;

import com.example.school.domain.entities.PaymentPlan;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentPlanRepositoryInterface {
    PaymentPlan save(PaymentPlan paymentPlan);

    Optional<PaymentPlan> findById(UUID id);

    List<PaymentPlan> findBySchool(UUID schoolId);

    void deleteById(UUID id);
}

