package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.TermDto;
import com.example.school.domain.services.TermServiceInterface;
import com.example.school.presenation.validators.TermRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/terms")
@RequiredArgsConstructor
@CrossOrigin
public class TermController {

    private final TermServiceInterface termService;

    @PostMapping
    public ResponseEntity<ApiResponse<TermDto>> createTerm(@Valid @RequestBody TermRequestValidator request) {
        TermDto dto = termService.createTerm(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Trimestre créé avec succès", dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TermDto>>> getAllTerms() {
        List<TermDto> terms = termService.getAllTerms();
        return ResponseEntity.ok(new ApiResponse<>(200, "Trimestres récupérés avec succès", terms));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TermDto>> getTermById(@PathVariable UUID id) {
        TermDto dto = termService.getTermById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Trimestre récupéré avec succès", dto));
    }

    @GetMapping("/academic-year/{academicYearId}")
    public ResponseEntity<ApiResponse<List<TermDto>>> getTermsByAcademicYear(@PathVariable UUID academicYearId) {
        List<TermDto> terms = termService.getTermsByAcademicYear(academicYearId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Trimestres de l'année académique récupérés avec succès", terms));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TermDto>> updateTerm(
            @PathVariable UUID id,
            @Valid @RequestBody TermRequestValidator request) {
        TermDto dto = termService.updateTerm(id, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Trimestre mis à jour avec succès", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTerm(@PathVariable UUID id) {
        termService.deleteTerm(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Trimestre supprimé avec succès", null));
    }

    @PostMapping("/{termId}/set-active")
    public ResponseEntity<ApiResponse<TermDto>> setActiveTerm(
            @PathVariable UUID termId,
            @RequestParam UUID academicYearId) {
        TermDto dto = termService.setActiveTerm(academicYearId, termId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Trimestre activé avec succès", dto));
    }
}

