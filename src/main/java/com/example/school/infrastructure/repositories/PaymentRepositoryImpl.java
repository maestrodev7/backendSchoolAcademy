package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.Payment;
import com.example.school.domain.repositories.PaymentRepositoryInterface;
import com.example.school.infrastructure.mappers.PaymentMapper;
import com.example.school.infrastructure.models.PaymentModel;
import com.example.school.infrastructure.repositories.jpa.JpaPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepositoryInterface {

    private final JpaPaymentRepository jpaRepository;

    @Override
    public Payment save(Payment payment) {
        PaymentModel model = PaymentMapper.toModel(payment);
        PaymentModel saved = jpaRepository.save(model);
        return PaymentMapper.toDomain(saved);
    }

    @Override
    public Optional<Payment> findById(UUID id) {
        return jpaRepository.findById(id).map(PaymentMapper::toDomain);
    }

    @Override
    public List<Payment> findByRegistration(UUID registrationId) {
        return jpaRepository.findByRegistration_Id(registrationId)
                .stream()
                .map(PaymentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Payment> findByStudent(UUID studentId) {
        return jpaRepository.findByRegistration_Student_Id(studentId)
                .stream()
                .map(PaymentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
