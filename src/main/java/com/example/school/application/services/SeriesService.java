package com.example.school.application.services;

import com.example.school.common.dto.SeriesDto;
import com.example.school.common.mapper.SeriesMapper;
import com.example.school.domain.entities.Series;
import com.example.school.domain.repositories.SeriesRepositoryInterface;
import com.example.school.domain.services.SeriesServiceInterface;
import com.example.school.presenation.validators.SeriesRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeriesService implements SeriesServiceInterface {

    private final SeriesRepositoryInterface seriesRepository;

    @Override
    public SeriesDto createSeries(SeriesRequestValidator request) {
        Series series = new Series();
        series.setCode(request.getCode());
        series.setName(request.getName());

        Series saved = seriesRepository.save(series);
        return SeriesMapper.toDto(saved);
    }

    @Override
    public List<SeriesDto> getAllSeries() {
        return seriesRepository.findAll()
                .stream()
                .map(SeriesMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SeriesDto getSeriesById(UUID id) {
        return seriesRepository.findById(id)
                .map(SeriesMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Série non trouvée"));
    }

    @Override
    public SeriesDto updateSeries(UUID id, SeriesRequestValidator request) {
        Series existing = seriesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Série non trouvée"));

        existing.setCode(request.getCode());
        existing.setName(request.getName());

        Series updated = seriesRepository.save(existing);
        return SeriesMapper.toDto(updated);
    }

    @Override
    public void deleteSeries(UUID id) {
        seriesRepository.deleteById(id);
    }
}
