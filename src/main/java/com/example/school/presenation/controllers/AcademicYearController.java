package com.example.school.presenation.controllers;

import com.example.school.common.dto.AcademicYearDto;
import com.example.school.common.dto.ApiResponse;
import com.example.school.domain.services.AcademicYearServiceInterface;
import com.example.school.presenation.validators.AcademicYearRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/academic-years")
@RequiredArgsConstructor
public class AcademicYearController {

    private final AcademicYearServiceInterface academicYearService;

    @PostMapping
    public ResponseEntity<ApiResponse<AcademicYearDto>> createAcademicYear(
            @Valid @RequestBody AcademicYearRequestValidator request) {
        AcademicYearDto year = academicYearService.createAcademicYear(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Année académique créée avec succès", year));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AcademicYearDto>>> getAllAcademicYears() {
        List<AcademicYearDto> years = academicYearService.getAllAcademicYears();
        return ResponseEntity.ok(new ApiResponse<>(200, "Liste des années académiques récupérée avec succès", years));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AcademicYearDto>> getAcademicYearById(@PathVariable UUID id) {
        AcademicYearDto year = academicYearService.getAcademicYearById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Année académique récupérée avec succès", year));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AcademicYearDto>> updateAcademicYear(
            @PathVariable UUID id,
            @Valid @RequestBody AcademicYearRequestValidator request) {
        AcademicYearDto updatedYear = academicYearService.updateAcademicYear(id, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Année académique mise à jour avec succès", updatedYear));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAcademicYear(@PathVariable UUID id) {
        academicYearService.deleteAcademicYear(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Année académique supprimée avec succès", null));
    }
}
