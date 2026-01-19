package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.GradeDto;
import com.example.school.domain.services.GradeServiceInterface;
import com.example.school.presenation.validators.ClassGradesBulkRequest;
import com.example.school.presenation.validators.GradeRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
@CrossOrigin
public class GradeController {

    private final GradeServiceInterface gradeService;

    @PostMapping
    public ResponseEntity<ApiResponse<GradeDto>> createGrade(@Valid @RequestBody GradeRequestValidator request) {
        GradeDto dto = gradeService.createGrade(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Note créée avec succès", dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GradeDto>>> getAllGrades() {
        List<GradeDto> grades = gradeService.getAllGrades();
        return ResponseEntity.ok(new ApiResponse<>(200, "Notes récupérées avec succès", grades));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GradeDto>> getGradeById(@PathVariable UUID id) {
        GradeDto dto = gradeService.getGradeById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Note récupérée avec succès", dto));
    }

    @GetMapping("/student/{studentId}/term/{termId}")
    public ResponseEntity<ApiResponse<List<GradeDto>>> getGradesByStudentAndTerm(
            @PathVariable UUID studentId,
            @PathVariable UUID termId) {
        List<GradeDto> grades = gradeService.getGradesByStudentIdAndTermId(studentId, termId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Notes récupérées avec succès", grades));
    }

    @GetMapping("/student/{studentId}/sequence/{sequenceId}")
    public ResponseEntity<ApiResponse<List<GradeDto>>> getGradesByStudentAndSequence(
            @PathVariable UUID studentId,
            @PathVariable UUID sequenceId) {
        List<GradeDto> grades = gradeService.getGradesByStudentIdAndSequenceId(studentId, sequenceId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Notes récupérées avec succès", grades));
    }

    @GetMapping("/student/{studentId}/academic-year/{academicYearId}")
    public ResponseEntity<ApiResponse<List<GradeDto>>> getGradesByStudentAndAcademicYear(
            @PathVariable UUID studentId,
            @PathVariable UUID academicYearId) {
        List<GradeDto> grades = gradeService.getGradesByStudentIdAndAcademicYearId(studentId, academicYearId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Notes récupérées avec succès", grades));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GradeDto>> updateGrade(
            @PathVariable UUID id,
            @Valid @RequestBody GradeRequestValidator request) {
        GradeDto dto = gradeService.updateGrade(id, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Note mise à jour avec succès", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteGrade(@PathVariable UUID id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Note supprimée avec succès", null));
    }

    /**
     * Enregistre ou met à jour en une seule fois les notes
     * d'une classe pour une compétence donnée (trimestre + séquence optionnelle).
     */
    @PostMapping("/class/{classRoomId}/bulk")
    public ResponseEntity<ApiResponse<Void>> saveClassGrades(
            @PathVariable UUID classRoomId,
            @Valid @RequestBody ClassGradesBulkRequest request
    ) {
        gradeService.saveOrUpdateClassGrades(classRoomId, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Notes de la classe enregistrées avec succès", null));
    }
}

