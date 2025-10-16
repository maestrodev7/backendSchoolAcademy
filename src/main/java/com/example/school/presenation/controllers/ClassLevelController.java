package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.ClassLevelDto;
import com.example.school.domain.services.ClassLevelServiceInterface;
import com.example.school.presenation.validators.ClassLevelRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/class-levels")
@RequiredArgsConstructor
public class ClassLevelController {

    private final ClassLevelServiceInterface classLevelService;

    @PostMapping
    public ResponseEntity<ApiResponse<ClassLevelDto>> create(@Valid @RequestBody ClassLevelRequestValidator request) {
        ClassLevelDto dto = classLevelService.createClassLevel(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Niveau de classe créé avec succès", dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClassLevelDto>>> getAll() {
        List<ClassLevelDto> list = classLevelService.getAllClassLevels();
        return ResponseEntity.ok(new ApiResponse<>(200, "Liste des niveaux de classe récupérée avec succès", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassLevelDto>> getById(@PathVariable UUID id) {
        ClassLevelDto dto = classLevelService.getClassLevelById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Niveau de classe récupéré avec succès", dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassLevelDto>> update(
            @PathVariable UUID id,
            @Valid @RequestBody ClassLevelRequestValidator request
    ) {
        ClassLevelDto dto = classLevelService.updateClassLevel(id, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Niveau de classe mis à jour avec succès", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        classLevelService.deleteClassLevel(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Niveau de classe supprimé avec succès", null));
    }
}
