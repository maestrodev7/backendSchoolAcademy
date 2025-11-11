package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.SchoolFeeConfig;
import com.example.school.domain.repositories.SchoolFeeConfigRepositoryInterface;
import com.example.school.infrastructure.mappers.SchoolFeeConfigMapper;
import com.example.school.infrastructure.models.SchoolFeeConfigModel;
import com.example.school.infrastructure.repositories.jpa.JpaSchoolFeeConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SchoolFeeConfigRepositoryImpl implements SchoolFeeConfigRepositoryInterface {

    private final JpaSchoolFeeConfigRepository jpaRepository;

    @Override
    public SchoolFeeConfig save(SchoolFeeConfig config) {
        SchoolFeeConfigModel model = SchoolFeeConfigMapper.toModel(config);
        SchoolFeeConfigModel saved = jpaRepository.save(model);
        return SchoolFeeConfigMapper.toDomain(saved);
    }

    @Override
    public Optional<SchoolFeeConfig> findBySchool(UUID schoolId) {
        return jpaRepository.findBySchool_Id(schoolId).map(SchoolFeeConfigMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
