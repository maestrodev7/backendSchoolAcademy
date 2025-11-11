package com.example.school.domain.repositories;

import com.example.school.domain.entities.FeeType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FeeTypeRepositoryInterface {
    FeeType save(FeeType feeType);

    Optional<FeeType> findById(UUID id);

    List<FeeType> findBySchool(UUID schoolId);

    void deleteById(UUID id);
}

