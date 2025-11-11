package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface JpaPaymentRepository extends JpaRepository<PaymentModel, UUID> {
    List<PaymentModel> findByRegistration_Id(UUID registrationId);

    List<PaymentModel> findByRegistration_Student_Id(UUID studentId);
}
