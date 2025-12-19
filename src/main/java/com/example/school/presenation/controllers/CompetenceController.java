package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.CompetenceDto;
import com.example.school.domain.services.CompetenceServiceInterface;
import com.example.school.presenation.validators.CompetenceRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/competences")
@RequiredArgsConstructor
@CrossOrigin
public class CompetenceController {

    private final CompetenceServiceInterface competenceService;

    @PostMapping
    public ResponseEntity<ApiResponse<CompetenceDto>> createCompetence(@Valid @RequestBody CompetenceRequestValidator request) {
        CompetenceDto dto = competenceService.createCompetence(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Compétence créée avec succès", dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CompetenceDto>>> getAllCompetences() {
        List<CompetenceDto> competences = competenceService.getAllCompetences();
        return ResponseEntity.ok(new ApiResponse<>(200, "Compétences récupérées avec succès", competences));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompetenceDto>> getCompetenceById(@PathVariable UUID id) {
        CompetenceDto dto = competenceService.getCompetenceById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Compétence récupérée avec succès", dto));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<ApiResponse<List<CompetenceDto>>> getCompetencesBySubject(@PathVariable UUID subjectId) {
        List<CompetenceDto> competences = competenceService.getCompetencesBySubjectId(subjectId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Compétences récupérées avec succès", competences));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CompetenceDto>> updateCompetence(
            @PathVariable UUID id,
            @Valid @RequestBody CompetenceRequestValidator request) {
        CompetenceDto dto = competenceService.updateCompetence(id, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Compétence mise à jour avec succès", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCompetence(@PathVariable UUID id) {
        competenceService.deleteCompetence(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Compétence supprimée avec succès", null));
    }
}

