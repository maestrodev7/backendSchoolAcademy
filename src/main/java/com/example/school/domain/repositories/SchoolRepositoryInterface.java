package com.example.school.domain.repositories;

import com.example.school.domain.entities.School;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SchoolRepositoryInterface {
    School save(School school);
    Optional<School> findById(UUID id);
    List<School> findAll();
    void deleteById(UUID id);
    boolean existsById(UUID id);
    boolean existsByName(String name);
}
