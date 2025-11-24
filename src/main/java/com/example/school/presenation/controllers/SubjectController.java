package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.SubjectDto;
import com.example.school.domain.services.SubjectServiceInterface;
import com.example.school.presenation.validators.SubjectRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectServiceInterface subjectService;

    @PostMapping
    public ResponseEntity<ApiResponse<SubjectDto>> create(@Valid @RequestBody SubjectRequestValidator request) {
        SubjectDto dto = subjectService.createSubject(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Matière créée avec succès", dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubjectDto>>> getAll() {
        List<SubjectDto> list = subjectService.getAllSubjects();
        return ResponseEntity.ok(new ApiResponse<>(200, "Liste des matières récupérée avec succès", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectDto>> getById(@PathVariable UUID id) {
        SubjectDto dto = subjectService.getSubjectById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Matière récupérée avec succès", dto));
    }

    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<ApiResponse<List<SubjectDto>>> getByClassroom(@PathVariable UUID classroomId) {
        List<SubjectDto> list = subjectService.getSubjectsByClassroom(classroomId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Matières de la classe récupérées avec succès", list));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectDto>> update(
            @PathVariable UUID id,
            @Valid @RequestBody SubjectRequestValidator request
    ) {
        SubjectDto dto = subjectService.updateSubject(id, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Matière mise à jour avec succès", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Matière supprimée avec succès", null));
    }
}

