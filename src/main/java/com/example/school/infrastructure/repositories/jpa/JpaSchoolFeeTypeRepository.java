package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.SchoolFeeTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface JpaSchoolFeeTypeRepository extends JpaRepository<SchoolFeeTypeModel, UUID> {
    List<SchoolFeeTypeModel> findBySchoolId(UUID schoolId);
}
