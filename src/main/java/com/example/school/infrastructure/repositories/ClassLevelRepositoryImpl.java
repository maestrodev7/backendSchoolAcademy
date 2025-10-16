package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.ClassLevel;
import com.example.school.domain.repositories.ClassLevelRepositoryInterface;
import com.example.school.infrastructure.mappers.ClassLevelMapper;
import com.example.school.infrastructure.models.ClassLevelModel;
import com.example.school.infrastructure.repositories.jpa.JpaClassLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ClassLevelRepositoryImpl implements ClassLevelRepositoryInterface {

    private final JpaClassLevelRepository jpaRepository;

    @Override
    public ClassLevel save(ClassLevel classLevel) {
        ClassLevelModel model = ClassLevelMapper.toModel(classLevel);
        return ClassLevelMapper.toDomain(jpaRepository.save(model));
    }

    @Override
    public Optional<ClassLevel> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(ClassLevelMapper::toDomain);
    }

    @Override
    public List<ClassLevel> findAll() {
        return jpaRepository.findAll().stream()
                .map(ClassLevelMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
