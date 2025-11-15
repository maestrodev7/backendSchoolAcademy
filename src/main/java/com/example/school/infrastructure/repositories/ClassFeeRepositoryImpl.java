package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.ClassFee;
import com.example.school.domain.repositories.ClassFeeRepositoryInterface;
import com.example.school.infrastructure.mappers.ClassFeeMapper;
import com.example.school.infrastructure.models.ClassFeeModel;
import com.example.school.infrastructure.repositories.jpa.JpaClassFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ClassFeeRepositoryImpl implements ClassFeeRepositoryInterface {

    private final JpaClassFeeRepository jpaClassFeeRepository;

    @Override
    public ClassFee save(ClassFee classFee) {
        ClassFeeModel model = ClassFeeMapper.toModel(classFee);
        ClassFeeModel saved = jpaClassFeeRepository.save(model);
        return ClassFeeMapper.toDomain(saved);
    }

    @Override
    public Optional<ClassFee> findById(UUID id) {
        return jpaClassFeeRepository.findById(id).map(ClassFeeMapper::toDomain);
    }

    @Override
    public List<ClassFee> findByClassRoom(UUID classRoomId) {
        return jpaClassFeeRepository.findByClassRoom_Id(classRoomId)
                .stream()
                .map(ClassFeeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassFee> findBySchool(UUID schoolId) {
        return jpaClassFeeRepository.findByClassRoom_School_Id(schoolId)
                .stream()
                .map(ClassFeeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaClassFeeRepository.deleteById(id);
    }
}
