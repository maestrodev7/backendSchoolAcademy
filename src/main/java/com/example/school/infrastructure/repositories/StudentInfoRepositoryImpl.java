package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.StudentInfo;
import com.example.school.domain.repositories.StudentInfoRepositoryInterface;
import com.example.school.infrastructure.mappers.StudentInfoMapper;
import com.example.school.infrastructure.models.StudentInfoModel;
import com.example.school.infrastructure.repositories.jpa.JpaStudentInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StudentInfoRepositoryImpl implements StudentInfoRepositoryInterface {

    private final JpaStudentInfoRepository jpaStudentInfoRepository;

    @Override
    public StudentInfo save(StudentInfo studentInfo) {
        StudentInfoModel model = StudentInfoMapper.toModel(studentInfo);
        StudentInfoModel saved = jpaStudentInfoRepository.save(model);
        return StudentInfoMapper.toDomain(saved);
    }

    @Override
    public Optional<StudentInfo> findById(UUID id) {
        return jpaStudentInfoRepository.findById(id)
                .map(StudentInfoMapper::toDomain);
    }

    @Override
    public Optional<StudentInfo> findByStudentId(UUID studentId) {
        return jpaStudentInfoRepository.findByStudentId(studentId)
                .map(StudentInfoMapper::toDomain);
    }

    @Override
    public boolean existsByUniqueIdentifier(String uniqueIdentifier) {
        return jpaStudentInfoRepository.existsByUniqueIdentifier(uniqueIdentifier);
    }

    @Override
    public Optional<StudentInfo> findByUniqueIdentifier(String uniqueIdentifier) {
        return jpaStudentInfoRepository.findByUniqueIdentifier(uniqueIdentifier)
                .map(StudentInfoMapper::toDomain);
    }

    @Override
    public long countByYear(String year) {
        return jpaStudentInfoRepository.countByYear(year);
    }

    @Override
    public List<StudentInfo> findByStudentIds(List<UUID> studentIds) {
        return jpaStudentInfoRepository.findByStudentIds(studentIds)
                .stream()
                .map(StudentInfoMapper::toDomain)
                .collect(Collectors.toList());
    }
}

