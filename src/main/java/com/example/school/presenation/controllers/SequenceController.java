package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.SequenceDto;
import com.example.school.domain.services.SequenceServiceInterface;
import com.example.school.presenation.validators.SequenceRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sequences")
@RequiredArgsConstructor
@CrossOrigin
public class SequenceController {

    private final SequenceServiceInterface sequenceService;

    @PostMapping
    public ResponseEntity<ApiResponse<SequenceDto>> createSequence(@Valid @RequestBody SequenceRequestValidator request) {
        SequenceDto dto = sequenceService.createSequence(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Séquence créée avec succès", dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SequenceDto>>> getAllSequences() {
        List<SequenceDto> sequences = sequenceService.getAllSequences();
        return ResponseEntity.ok(new ApiResponse<>(200, "Séquences récupérées avec succès", sequences));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SequenceDto>> getSequenceById(@PathVariable UUID id) {
        SequenceDto dto = sequenceService.getSequenceById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Séquence récupérée avec succès", dto));
    }

    @GetMapping("/term/{termId}")
    public ResponseEntity<ApiResponse<List<SequenceDto>>> getSequencesByTerm(@PathVariable UUID termId) {
        List<SequenceDto> sequences = sequenceService.getSequencesByTerm(termId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Séquences du trimestre récupérées avec succès", sequences));
    }

    @GetMapping("/academic-year/{academicYearId}")
    public ResponseEntity<ApiResponse<List<SequenceDto>>> getSequencesByAcademicYear(@PathVariable UUID academicYearId) {
        List<SequenceDto> sequences = sequenceService.getSequencesByAcademicYear(academicYearId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Séquences de l'année académique récupérées avec succès", sequences));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SequenceDto>> updateSequence(
            @PathVariable UUID id,
            @Valid @RequestBody SequenceRequestValidator request) {
        SequenceDto dto = sequenceService.updateSequence(id, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Séquence mise à jour avec succès", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSequence(@PathVariable UUID id) {
        sequenceService.deleteSequence(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Séquence supprimée avec succès", null));
    }

    @PostMapping("/{sequenceId}/set-active")
    public ResponseEntity<ApiResponse<SequenceDto>> setActiveSequence(
            @PathVariable UUID sequenceId,
            @RequestParam UUID termId) {
        SequenceDto dto = sequenceService.setActiveSequence(termId, sequenceId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Séquence activée avec succès", dto));
    }
}

