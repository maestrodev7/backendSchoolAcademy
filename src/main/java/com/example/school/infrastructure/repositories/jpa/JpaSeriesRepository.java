package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.SeriesModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaSeriesRepository extends JpaRepository<SeriesModel, UUID> {
}
