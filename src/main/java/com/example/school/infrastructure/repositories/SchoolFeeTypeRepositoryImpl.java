package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.SchoolFeeType;
import com.example.school.domain.repositories.SchoolFeeTypeRepositoryInterface;
import com.example.school.infrastructure.mappers.SchoolFeeTypeMapper;
import com.example.school.infrastructure.models.SchoolFeeTypeModel;
import com.example.school.infrastructure.repositories.jpa.JpaSchoolFeeTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SchoolFeeTypeRepositoryImpl implements SchoolFeeTypeRepositoryInterface {

    private final JpaSchoolFeeTypeRepository jpaRepository;

    @Override
    public SchoolFeeType save(SchoolFeeType feeType) {
        SchoolFeeTypeModel model = SchoolFeeTypeMapper.toModel(feeType);
        SchoolFeeTypeModel saved = jpaRepository.save(model);
        return SchoolFeeTypeMapper.toDomain(saved);
    }

    @Override
    public Optional<SchoolFeeType> findById(UUID id) {
        return jpaRepository.findById(id).map(SchoolFeeTypeMapper::toDomain);
    }

    @Override
    public List<SchoolFeeType> findBySchool(UUID schoolId) {
        return jpaRepository.findBySchoolId(schoolId)
                .stream()
                .map(SchoolFeeTypeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
