package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.SeriesDto;
import com.example.school.domain.services.SeriesServiceInterface;
import com.example.school.presenation.validators.SeriesRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/series")
@RequiredArgsConstructor
public class SeriesController {

    private final SeriesServiceInterface seriesService;

    @PostMapping
    public ResponseEntity<ApiResponse<SeriesDto>> create(@Valid @RequestBody SeriesRequestValidator request) {
        SeriesDto dto = seriesService.createSeries(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Série créée avec succès", dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SeriesDto>>> getAll() {
        List<SeriesDto> list = seriesService.getAllSeries();
        return ResponseEntity.ok(new ApiResponse<>(200, "Liste des séries récupérée avec succès", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SeriesDto>> getById(@PathVariable UUID id) {
        SeriesDto dto = seriesService.getSeriesById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Série récupérée avec succès", dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SeriesDto>> update(
            @PathVariable UUID id,
            @Valid @RequestBody SeriesRequestValidator request
    ) {
        SeriesDto dto = seriesService.updateSeries(id, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Série mise à jour avec succès", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        seriesService.deleteSeries(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Série supprimée avec succès", null));
    }
}
