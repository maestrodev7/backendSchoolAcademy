package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.ClassLevelModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaClassLevelRepository extends JpaRepository<ClassLevelModel, UUID> {
}
