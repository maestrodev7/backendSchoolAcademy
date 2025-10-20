package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.ClassroomDto;
import com.example.school.domain.services.ClassroomServiceInterface;
import com.example.school.presenation.validators.ClassroomRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomServiceInterface classroomService;

    @PostMapping
    public ResponseEntity<ApiResponse<ClassroomDto>> create(@Valid @RequestBody ClassroomRequestValidator request) {
        ClassroomDto dto = classroomService.createClassroom(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Classe créée avec succès", dto));
    }

    @GetMapping("/school/{schoolId}")
    public ResponseEntity<ApiResponse<List<ClassroomDto>>> getBySchool(@PathVariable UUID schoolId) {
        List<ClassroomDto> list = classroomService.getClassroomsBySchool(schoolId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Classes de l'école récupérées avec succès", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassroomDto>> getById(@PathVariable UUID id) {
        ClassroomDto dto = classroomService.getClassroomById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Classe récupérée avec succès", dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassroomDto>> update(
            @PathVariable UUID id,
            @Valid @RequestBody ClassroomRequestValidator request
    ) {
        ClassroomDto dto = classroomService.updateClassroom(id, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Classe mise à jour avec succès", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        classroomService.deleteClassroom(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Classe supprimée avec succès", null));
    }
}
