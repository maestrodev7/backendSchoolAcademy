package com.example.school.domain.repositories;

import com.example.school.domain.entities.Payment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepositoryInterface {
    Payment save(Payment payment);

    Optional<Payment> findById(UUID id);

    List<Payment> findByRegistration(UUID registrationId);

    List<Payment> findByStudent(UUID studentId);

    void deleteById(UUID id);
}

