package com.example.school.domain.repositories;

import com.example.school.domain.entities.Otp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OtpRepositoryInterface {
    Otp save(Otp otp);
    Optional<Otp> findById(UUID id);
    Optional<Otp> findByUserIdAndCodeAndPurpose(UUID userId, String code, String purpose);
    Optional<Otp> findLatestByUserIdAndPurpose(UUID userId, String purpose);
    List<Otp> findByUserId(UUID userId);
    void deleteById(UUID id);
    void deleteExpiredOtps();
    void deleteByUserId(UUID userId);
}

