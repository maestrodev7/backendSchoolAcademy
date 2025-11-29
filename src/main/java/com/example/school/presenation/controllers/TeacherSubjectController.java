package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.TeacherSubjectDto;
import com.example.school.domain.services.TeacherSubjectServiceInterface;
import com.example.school.presenation.validators.TeacherSubjectRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Contrôleur pour gérer les associations Enseignant-Matière
 * Optimisé pour une excellente expérience utilisateur
 * Gère automatiquement l'année académique active de l'école
 */
@RestController
@RequestMapping("/api/schools/{schoolId}/teacher-subjects")
@RequiredArgsConstructor
public class TeacherSubjectController {

    private final TeacherSubjectServiceInterface teacherSubjectService;

    /**
     * Créer ou mettre à jour une association enseignant-matière
     * L'année académique active est automatiquement récupérée depuis l'école
     */
    @PostMapping
    public ResponseEntity<ApiResponse<TeacherSubjectDto>> create(
            @PathVariable UUID schoolId,
            @Valid @RequestBody TeacherSubjectRequestValidator request) {
        // S'assurer que l'schoolId dans le path correspond à celui du body
        request.setSchoolId(schoolId);
        TeacherSubjectDto dto = teacherSubjectService.create(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Association enseignant-matière créée ou mise à jour avec succès", dto));
    }

    /**
     * Récupérer toutes les associations enseignant-matière d'une école
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<TeacherSubjectDto>>> getAllBySchool(@PathVariable UUID schoolId) {
        List<TeacherSubjectDto> list = teacherSubjectService.getBySchool(schoolId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Associations enseignant-matière récupérées avec succès", list));
    }

    /**
     * Récupérer une association enseignant-matière par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherSubjectDto>> getById(@PathVariable UUID id) {
        TeacherSubjectDto dto = teacherSubjectService.getById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Association enseignant-matière récupérée avec succès", dto));
    }

    /**
     * Récupérer toutes les matières d'un enseignant dans une école
     */
    @GetMapping("/teacher/{userSchoolId}")
    public ResponseEntity<ApiResponse<List<TeacherSubjectDto>>> getByTeacher(
            @PathVariable UUID schoolId,
            @PathVariable UUID userSchoolId) {
        List<TeacherSubjectDto> list = teacherSubjectService.getByTeacherAndSchool(userSchoolId, schoolId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Matières de l'enseignant récupérées avec succès", list));
    }

    /**
     * Récupérer tous les enseignants d'une matière dans une école
     */
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<ApiResponse<List<TeacherSubjectDto>>> getBySubject(
            @PathVariable UUID schoolId,
            @PathVariable UUID subjectId) {
        List<TeacherSubjectDto> list = teacherSubjectService.getBySubjectAndSchool(subjectId, schoolId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Enseignants de la matière récupérés avec succès", list));
    }

    /**
     * Mettre à jour une association enseignant-matière
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherSubjectDto>> update(
            @PathVariable UUID id,
            @Valid @RequestBody TeacherSubjectRequestValidator request) {
        TeacherSubjectDto dto = teacherSubjectService.update(id, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Association enseignant-matière mise à jour avec succès", dto));
    }

    /**
     * Supprimer une association enseignant-matière
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        teacherSubjectService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Association enseignant-matière supprimée avec succès", null));
    }
}

