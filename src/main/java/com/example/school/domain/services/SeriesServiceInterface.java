package com.example.school.domain.services;

import com.example.school.common.dto.SeriesDto;
import com.example.school.presenation.validators.SeriesRequestValidator;

import java.util.List;
import java.util.UUID;

public interface SeriesServiceInterface {
    SeriesDto createSeries(SeriesRequestValidator request);
    List<SeriesDto> getAllSeries();
    SeriesDto getSeriesById(UUID id);
    SeriesDto updateSeries(UUID id, SeriesRequestValidator request);
    void deleteSeries(UUID id);
}
