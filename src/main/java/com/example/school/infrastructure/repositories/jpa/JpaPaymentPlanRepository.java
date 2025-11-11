package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.PaymentPlanModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaPaymentPlanRepository extends JpaRepository<PaymentPlanModel, UUID> {
    List<PaymentPlanModel> findBySchool_Id(UUID schoolId);
}

