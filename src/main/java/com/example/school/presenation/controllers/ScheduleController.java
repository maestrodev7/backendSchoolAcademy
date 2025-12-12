package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.ScheduleDto;
import com.example.school.domain.services.ScheduleServiceInterface;
import com.example.school.presenation.validators.ScheduleRequestValidator;
import com.example.school.presenation.validators.UpdateScheduleValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

/**
 * Contrôleur pour gérer l'emploi du temps
 * Optimisé pour une excellente expérience utilisateur
 * Gère automatiquement l'année académique active de l'école
 */
@RestController
@RequestMapping("/api/schools/{schoolId}/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleServiceInterface scheduleService;

    /**
     * Créer un nouveau créneau d'emploi du temps
     * L'année académique active est automatiquement récupérée depuis l'école
     * Vérifie automatiquement les conflits d'horaires
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ScheduleDto>> create(
            @PathVariable UUID schoolId,
            @Valid @RequestBody ScheduleRequestValidator request) {
        // S'assurer que l'schoolId dans le path correspond à celui du body
        request.setSchoolId(schoolId);
        ScheduleDto dto = scheduleService.create(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Créneau d'emploi du temps créé avec succès", dto));
    }

    /**
     * Récupérer tous les créneaux d'une école
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ScheduleDto>>> getAllBySchool(@PathVariable UUID schoolId) {
        List<ScheduleDto> list = scheduleService.getBySchool(schoolId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Emploi du temps récupéré avec succès", list));
    }

    /**
     * Récupérer un créneau par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ScheduleDto>> getById(@PathVariable UUID id) {
        ScheduleDto dto = scheduleService.getById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Créneau d'emploi du temps récupéré avec succès", dto));
    }

    /**
     * Récupérer l'emploi du temps d'une classe
     */
    @GetMapping("/classroom/{classRoomId}")
    public ResponseEntity<ApiResponse<List<ScheduleDto>>> getByClassRoom(@PathVariable UUID classRoomId) {
        List<ScheduleDto> list = scheduleService.getByClassRoom(classRoomId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Emploi du temps de la classe récupéré avec succès", list));
    }

    /**
     * Récupérer l'emploi du temps d'une classe pour un jour spécifique
     */
    @GetMapping("/classroom/{classRoomId}/day/{dayOfWeek}")
    public ResponseEntity<ApiResponse<List<ScheduleDto>>> getByClassRoomAndDay(
            @PathVariable UUID classRoomId,
            @PathVariable DayOfWeek dayOfWeek) {
        List<ScheduleDto> list = scheduleService.getByClassRoomAndDay(classRoomId, dayOfWeek);
        return ResponseEntity.ok(new ApiResponse<>(200, "Emploi du temps du jour récupéré avec succès", list));
    }

    /**
     * Récupérer l'emploi du temps d'un enseignant
     */
    @GetMapping("/teacher/{teacherSubjectId}")
    public ResponseEntity<ApiResponse<List<ScheduleDto>>> getByTeacher(@PathVariable UUID teacherSubjectId) {
        List<ScheduleDto> list = scheduleService.getByTeacher(teacherSubjectId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Emploi du temps de l'enseignant récupéré avec succès", list));
    }

    /**
     * Récupérer l'emploi du temps d'un enseignant pour un jour spécifique
     */
    @GetMapping("/teacher/{teacherSubjectId}/day/{dayOfWeek}")
    public ResponseEntity<ApiResponse<List<ScheduleDto>>> getByTeacherAndDay(
            @PathVariable UUID teacherSubjectId,
            @PathVariable DayOfWeek dayOfWeek) {
        List<ScheduleDto> list = scheduleService.getByTeacherAndDay(teacherSubjectId, dayOfWeek);
        return ResponseEntity.ok(new ApiResponse<>(200, "Emploi du temps du jour récupéré avec succès", list));
    }

    /**
     * Récupérer l'emploi du temps d'une année académique
     */
    @GetMapping("/academic-year/{academicYearId}")
    public ResponseEntity<ApiResponse<List<ScheduleDto>>> getByAcademicYear(
            @PathVariable UUID academicYearId) {
        List<ScheduleDto> list = scheduleService.getByAcademicYear(academicYearId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Emploi du temps de l'année récupéré avec succès", list));
    }

    /**
     * Mettre à jour un créneau d'emploi du temps
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ScheduleDto>> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateScheduleValidator request) {
        ScheduleDto dto = scheduleService.update(id, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Créneau d'emploi du temps mis à jour avec succès", dto));
    }

    /**
     * Supprimer un créneau d'emploi du temps
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        scheduleService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Créneau d'emploi du temps supprimé avec succès", null));
    }

    /**
     * Supprimer tous les créneaux d'une classe
     */
    @DeleteMapping("/classroom/{classRoomId}")
    public ResponseEntity<ApiResponse<Void>> deleteByClassRoom(@PathVariable UUID classRoomId) {
        scheduleService.deleteByClassRoom(classRoomId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Tous les créneaux de la classe ont été supprimés avec succès", null));
    }
}

