package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.Series;
import com.example.school.domain.repositories.SeriesRepositoryInterface;
import com.example.school.infrastructure.mappers.SeriesMapper;
import com.example.school.infrastructure.models.SeriesModel;
import com.example.school.infrastructure.repositories.jpa.JpaSeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SeriesRepositoryImpl implements SeriesRepositoryInterface {

    private final JpaSeriesRepository jpaRepository;

    @Override
    public Series save(Series series) {
        SeriesModel model = SeriesMapper.toModel(series);
        return SeriesMapper.toDomain(jpaRepository.save(model));
    }

    @Override
    public Optional<Series> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(SeriesMapper::toDomain);
    }

    @Override
    public List<Series> findAll() {
        return jpaRepository.findAll().stream()
                .map(SeriesMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
