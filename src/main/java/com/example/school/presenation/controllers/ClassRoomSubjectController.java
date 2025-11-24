package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.ClassRoomSubjectDto;
import com.example.school.domain.services.ClassRoomSubjectServiceInterface;
import com.example.school.presenation.validators.ClassRoomSubjectRequestValidator;
import com.example.school.presenation.validators.UpdateClassRoomSubjectValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/classroom-subjects")
@RequiredArgsConstructor
public class ClassRoomSubjectController {

    private final ClassRoomSubjectServiceInterface classRoomSubjectService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClassRoomSubjectDto>>> getAll() {
        List<ClassRoomSubjectDto> list = classRoomSubjectService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(200, "Liste des associations matière-classe récupérée avec succès", list));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClassRoomSubjectDto>> create(
            @Valid @RequestBody ClassRoomSubjectRequestValidator request) {
        ClassRoomSubjectDto dto = classRoomSubjectService.create(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Association matière-classe créée ou mise à jour avec succès", dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassRoomSubjectDto>> getById(@PathVariable UUID id) {
        ClassRoomSubjectDto dto = classRoomSubjectService.getById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Association matière-classe récupérée avec succès", dto));
    }

    @GetMapping("/classroom/{classRoomId}")
    public ResponseEntity<ApiResponse<List<ClassRoomSubjectDto>>> getByClassRoom(@PathVariable UUID classRoomId) {
        List<ClassRoomSubjectDto> list = classRoomSubjectService.getByClassRoom(classRoomId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Associations de la classe récupérées avec succès", list));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<ApiResponse<List<ClassRoomSubjectDto>>> getBySubject(@PathVariable UUID subjectId) {
        List<ClassRoomSubjectDto> list = classRoomSubjectService.getBySubject(subjectId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Associations de la matière récupérées avec succès", list));
    }

    @GetMapping("/classroom/{classRoomId}/subject/{subjectId}")
    public ResponseEntity<ApiResponse<ClassRoomSubjectDto>> getByClassRoomAndSubject(
            @PathVariable UUID classRoomId,
            @PathVariable UUID subjectId) {
        ClassRoomSubjectDto dto = classRoomSubjectService.getByClassRoomAndSubject(classRoomId, subjectId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Association matière-classe récupérée avec succès", dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassRoomSubjectDto>> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateClassRoomSubjectValidator request) {
        ClassRoomSubjectDto dto = classRoomSubjectService.update(id, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Association matière-classe mise à jour avec succès", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        classRoomSubjectService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Association matière-classe supprimée avec succès", null));
    }

    @DeleteMapping("/classroom/{classRoomId}/subject/{subjectId}")
    public ResponseEntity<ApiResponse<Void>> deleteByClassRoomAndSubject(
            @PathVariable UUID classRoomId,
            @PathVariable UUID subjectId) {
        classRoomSubjectService.deleteByClassRoomAndSubject(classRoomId, subjectId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Association matière-classe supprimée avec succès", null));
    }
}

