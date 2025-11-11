package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.FeeType;
import com.example.school.domain.repositories.FeeTypeRepositoryInterface;
import com.example.school.infrastructure.mappers.FeeTypeMapper;
import com.example.school.infrastructure.models.FeeTypeModel;
import com.example.school.infrastructure.repositories.jpa.JpaFeeTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FeeTypeRepositoryImpl implements FeeTypeRepositoryInterface {

    private final JpaFeeTypeRepository jpaFeeTypeRepository;

    @Override
    public FeeType save(FeeType feeType) {
        FeeTypeModel model = FeeTypeMapper.toModel(feeType);
        FeeTypeModel saved = jpaFeeTypeRepository.save(model);
        return FeeTypeMapper.toDomain(saved);
    }

    @Override
    public Optional<FeeType> findById(UUID id) {
        return jpaFeeTypeRepository.findById(id).map(FeeTypeMapper::toDomain);
    }

    @Override
    public List<FeeType> findBySchool(UUID schoolId) {
        return jpaFeeTypeRepository.findBySchool_Id(schoolId)
                .stream()
                .map(FeeTypeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaFeeTypeRepository.deleteById(id);
    }
}

