package com.example.school.domain.repositories;

import com.example.school.domain.entities.SchoolFeeType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SchoolFeeTypeRepositoryInterface {
    SchoolFeeType save(SchoolFeeType feeType);

    Optional<SchoolFeeType> findById(UUID id);

    List<SchoolFeeType> findBySchool(UUID schoolId);

    void deleteById(UUID id);
}

