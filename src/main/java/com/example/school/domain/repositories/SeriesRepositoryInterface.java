package com.example.school.domain.repositories;

import com.example.school.domain.entities.Series;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeriesRepositoryInterface {
    Series save(Series series);
    Optional<Series> findById(UUID id);
    List<Series> findAll();
    void deleteById(UUID id);
}
