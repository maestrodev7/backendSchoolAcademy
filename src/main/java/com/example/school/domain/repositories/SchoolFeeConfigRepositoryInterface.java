package com.example.school.domain.repositories;

import com.example.school.domain.entities.SchoolFeeConfig;

import java.util.Optional;
import java.util.UUID;

public interface SchoolFeeConfigRepositoryInterface {
    SchoolFeeConfig save(SchoolFeeConfig config);

    Optional<SchoolFeeConfig> findBySchool(UUID schoolId);

    void deleteById(UUID id);
}

