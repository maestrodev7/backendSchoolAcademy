package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.AbsenceDto;
import com.example.school.domain.services.AbsenceServiceInterface;
import com.example.school.presenation.validators.AbsenceRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/absences")
@RequiredArgsConstructor
@CrossOrigin
public class AbsenceController {

    private final AbsenceServiceInterface absenceService;

    @PostMapping
    public ResponseEntity<ApiResponse<List<AbsenceDto>>> createAbsences(
            @Valid @RequestBody AbsenceRequestValidator request) {
        List<AbsenceDto> dtos = absenceService.createAbsences(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Absences enregistrées avec succès", dtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AbsenceDto>> getAbsenceById(@PathVariable UUID id) {
        AbsenceDto dto = absenceService.getAbsenceById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Absence récupérée avec succès", dto));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<AbsenceDto>>> getAbsencesByStudent(@PathVariable UUID studentId) {
        List<AbsenceDto> dtos = absenceService.getAbsencesByStudent(studentId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Absences récupérées avec succès", dtos));
    }

    @GetMapping("/student/{studentId}/date-range")
    public ResponseEntity<ApiResponse<List<AbsenceDto>>> getAbsencesByStudentAndDateRange(
            @PathVariable UUID studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AbsenceDto> dtos = absenceService.getAbsencesByStudentAndDateRange(studentId, startDate, endDate);
        return ResponseEntity.ok(new ApiResponse<>(200, "Absences récupérées avec succès", dtos));
    }

    @GetMapping("/student/{studentId}/total-hours")
    public ResponseEntity<ApiResponse<Double>> getTotalAbsenceHoursByStudentAndDateRange(
            @PathVariable UUID studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Double totalHours = absenceService.getTotalAbsenceHoursByStudentAndDateRange(studentId, startDate, endDate);
        return ResponseEntity.ok(new ApiResponse<>(200, "Total d'heures d'absence récupéré avec succès", totalHours));
    }

    @GetMapping("/class/{classRoomId}")
    public ResponseEntity<ApiResponse<List<AbsenceDto>>> getAbsencesByClassRoom(@PathVariable UUID classRoomId) {
        List<AbsenceDto> dtos = absenceService.getAbsencesByClassRoom(classRoomId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Absences récupérées avec succès", dtos));
    }

    @GetMapping("/class/{classRoomId}/date/{date}")
    public ResponseEntity<ApiResponse<List<AbsenceDto>>> getAbsencesByClassRoomAndDate(
            @PathVariable UUID classRoomId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AbsenceDto> dtos = absenceService.getAbsencesByClassRoomAndDate(classRoomId, date);
        return ResponseEntity.ok(new ApiResponse<>(200, "Absences récupérées avec succès", dtos));
    }

    @GetMapping("/class/{classRoomId}/date-range")
    public ResponseEntity<ApiResponse<List<AbsenceDto>>> getAbsencesByClassRoomAndDateRange(
            @PathVariable UUID classRoomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AbsenceDto> dtos = absenceService.getAbsencesByClassRoomAndDateRange(classRoomId, startDate, endDate);
        return ResponseEntity.ok(new ApiResponse<>(200, "Absences récupérées avec succès", dtos));
    }

    @GetMapping("/student/{studentId}/class/{classRoomId}/date-range")
    public ResponseEntity<ApiResponse<List<AbsenceDto>>> getAbsencesByStudentAndClassRoomAndDateRange(
            @PathVariable UUID studentId,
            @PathVariable UUID classRoomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AbsenceDto> dtos = absenceService.getAbsencesByStudentAndClassRoomAndDateRange(studentId, classRoomId, startDate, endDate);
        return ResponseEntity.ok(new ApiResponse<>(200, "Absences récupérées avec succès", dtos));
    }

    @GetMapping("/class/{classRoomId}/subject/{subjectId}/date/{date}")
    public ResponseEntity<ApiResponse<List<AbsenceDto>>> getAbsencesByClassRoomAndSubjectAndDate(
            @PathVariable UUID classRoomId,
            @PathVariable UUID subjectId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AbsenceDto> dtos = absenceService.getAbsencesByClassRoomAndSubjectAndDate(classRoomId, subjectId, date);
        return ResponseEntity.ok(new ApiResponse<>(200, "Absences récupérées avec succès", dtos));
    }

    @GetMapping("/student/{studentId}/academic-year/{academicYearId}")
    public ResponseEntity<ApiResponse<List<AbsenceDto>>> getAbsencesByStudentAndAcademicYear(
            @PathVariable UUID studentId,
            @PathVariable UUID academicYearId) {
        List<AbsenceDto> dtos = absenceService.getAbsencesByStudentAndAcademicYear(studentId, academicYearId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Absences récupérées avec succès", dtos));
    }

    @GetMapping("/class/{classRoomId}/academic-year/{academicYearId}")
    public ResponseEntity<ApiResponse<List<AbsenceDto>>> getAbsencesByClassRoomAndAcademicYear(
            @PathVariable UUID classRoomId,
            @PathVariable UUID academicYearId) {
        List<AbsenceDto> dtos = absenceService.getAbsencesByClassRoomAndAcademicYear(classRoomId, academicYearId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Absences récupérées avec succès", dtos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAbsence(@PathVariable UUID id) {
        absenceService.deleteAbsence(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Absence supprimée avec succès", null));
    }
}
