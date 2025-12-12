package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.OtpModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaOtpRepository extends JpaRepository<OtpModel, UUID> {
    
    Optional<OtpModel> findByUserIdAndCodeAndPurpose(UUID userId, String code, String purpose);
    
    @Query("SELECT o FROM OtpModel o WHERE o.userId = :userId AND o.purpose = :purpose ORDER BY o.expiresAt DESC")
    Optional<OtpModel> findLatestByUserIdAndPurpose(@Param("userId") UUID userId, @Param("purpose") String purpose);
    
    List<OtpModel> findByUserId(UUID userId);
    
    @Modifying
    @Query("DELETE FROM OtpModel o WHERE o.expiresAt < :now")
    void deleteExpiredOtps(@Param("now") LocalDateTime now);
    
    @Modifying
    @Query("DELETE FROM OtpModel o WHERE o.userId = :userId")
    void deleteByUserId(@Param("userId") UUID userId);
}

