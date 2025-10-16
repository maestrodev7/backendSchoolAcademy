package com.example.school.domain.repositories;

import com.example.school.domain.entities.ClassLevel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClassLevelRepositoryInterface {
    ClassLevel save(ClassLevel classLevel);
    Optional<ClassLevel> findById(UUID id);
    List<ClassLevel> findAll();
    void deleteById(UUID id);
}
