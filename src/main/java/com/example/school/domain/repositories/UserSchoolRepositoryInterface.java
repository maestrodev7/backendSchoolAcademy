package com.example.school.domain.repositories;

import com.example.school.domain.entities.UserSchool;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserSchoolRepositoryInterface {
    UserSchool save(UserSchool userSchool);
    Optional<UserSchool> findById(UUID id);
    List<UserSchool> findAll();
    void deleteById(UUID id);
}
