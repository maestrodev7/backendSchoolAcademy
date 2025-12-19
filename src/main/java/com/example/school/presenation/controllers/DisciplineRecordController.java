package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.DisciplineRecordDto;
import com.example.school.domain.services.DisciplineRecordServiceInterface;
import com.example.school.presenation.validators.DisciplineRecordRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/discipline-records")
@RequiredArgsConstructor
@CrossOrigin
public class DisciplineRecordController {

    private final DisciplineRecordServiceInterface disciplineRecordService;

    @PostMapping
    public ResponseEntity<ApiResponse<DisciplineRecordDto>> createDisciplineRecord(@Valid @RequestBody DisciplineRecordRequestValidator request) {
        DisciplineRecordDto dto = disciplineRecordService.createDisciplineRecord(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Enregistrement disciplinaire créé avec succès", dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DisciplineRecordDto>> getDisciplineRecordById(@PathVariable UUID id) {
        DisciplineRecordDto dto = disciplineRecordService.getDisciplineRecordById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Enregistrement disciplinaire récupéré avec succès", dto));
    }

    @GetMapping("/student/{studentId}/term/{termId}")
    public ResponseEntity<ApiResponse<DisciplineRecordDto>> getDisciplineRecordByStudentAndTerm(
            @PathVariable UUID studentId,
            @PathVariable UUID termId) {
        DisciplineRecordDto dto = disciplineRecordService.getDisciplineRecordByStudentIdAndTermId(studentId, termId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Enregistrement disciplinaire récupéré avec succès", dto));
    }

    @GetMapping("/student/{studentId}/academic-year/{academicYearId}")
    public ResponseEntity<ApiResponse<List<DisciplineRecordDto>>> getDisciplineRecordsByStudentAndAcademicYear(
            @PathVariable UUID studentId,
            @PathVariable UUID academicYearId) {
        List<DisciplineRecordDto> records = disciplineRecordService.getDisciplineRecordsByStudentIdAndAcademicYearId(studentId, academicYearId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Enregistrements disciplinaires récupérés avec succès", records));
    }

    @GetMapping("/academic-year/{academicYearId}")
    public ResponseEntity<ApiResponse<List<DisciplineRecordDto>>> getDisciplineRecordsByAcademicYear(
            @PathVariable UUID academicYearId) {
        List<DisciplineRecordDto> records = disciplineRecordService.getDisciplineRecordsByAcademicYearId(academicYearId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Enregistrements disciplinaires récupérés avec succès", records));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DisciplineRecordDto>> updateDisciplineRecord(
            @PathVariable UUID id,
            @Valid @RequestBody DisciplineRecordRequestValidator request) {
        DisciplineRecordDto dto = disciplineRecordService.updateDisciplineRecord(id, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Enregistrement disciplinaire mis à jour avec succès", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDisciplineRecord(@PathVariable UUID id) {
        disciplineRecordService.deleteDisciplineRecord(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Enregistrement disciplinaire supprimé avec succès", null));
    }
}

