package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.FeeTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaFeeTypeRepository extends JpaRepository<FeeTypeModel, UUID> {
    List<FeeTypeModel> findBySchool_Id(UUID schoolId);
}

