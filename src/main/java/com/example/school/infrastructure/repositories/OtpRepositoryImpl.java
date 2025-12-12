package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.Otp;
import com.example.school.domain.repositories.OtpRepositoryInterface;
import com.example.school.infrastructure.mappers.OtpMapper;
import com.example.school.infrastructure.models.OtpModel;
import com.example.school.infrastructure.repositories.jpa.JpaOtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OtpRepositoryImpl implements OtpRepositoryInterface {

    private final JpaOtpRepository jpaOtpRepository;

    @Override
    public Otp save(Otp otp) {
        OtpModel model = OtpMapper.toModel(otp);
        OtpModel saved = jpaOtpRepository.save(model);
        return OtpMapper.toDomain(saved);
    }

    @Override
    public Optional<Otp> findById(UUID id) {
        return jpaOtpRepository.findById(id)
                .map(OtpMapper::toDomain);
    }

    @Override
    public Optional<Otp> findByUserIdAndCodeAndPurpose(UUID userId, String code, String purpose) {
        return jpaOtpRepository.findByUserIdAndCodeAndPurpose(userId, code, purpose)
                .map(OtpMapper::toDomain);
    }

    @Override
    public Optional<Otp> findLatestByUserIdAndPurpose(UUID userId, String purpose) {
        return jpaOtpRepository.findLatestByUserIdAndPurpose(userId, purpose)
                .map(OtpMapper::toDomain);
    }

    @Override
    public List<Otp> findByUserId(UUID userId) {
        return jpaOtpRepository.findByUserId(userId)
                .stream()
                .map(OtpMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaOtpRepository.deleteById(id);
    }

    @Override
    public void deleteExpiredOtps() {
        jpaOtpRepository.deleteExpiredOtps(LocalDateTime.now());
    }

    @Override
    public void deleteByUserId(UUID userId) {
        jpaOtpRepository.deleteByUserId(userId);
    }
}

