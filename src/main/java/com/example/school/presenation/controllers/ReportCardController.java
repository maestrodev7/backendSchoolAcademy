package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.ReportCardDto;
import com.example.school.domain.services.ReportCardServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/report-cards")
@RequiredArgsConstructor
@CrossOrigin
public class ReportCardController {

    private final ReportCardServiceInterface reportCardService;

    /**
     * Génère le bulletin scolaire d'un élève pour un trimestre
     * 
     * @param studentId ID de l'élève
     * @param classRoomId ID de la classe
     * @param academicYearId ID de l'année académique
     * @param termId ID du trimestre
     * @return Bulletin scolaire complet
     */
    @GetMapping("/student/{studentId}/class/{classRoomId}/academic-year/{academicYearId}/term/{termId}")
    public ResponseEntity<ApiResponse<ReportCardDto>> getReportCardForTerm(
            @PathVariable UUID studentId,
            @PathVariable UUID classRoomId,
            @PathVariable UUID academicYearId,
            @PathVariable UUID termId) {
        ReportCardDto reportCard = reportCardService.generateReportCardForTerm(studentId, classRoomId, academicYearId, termId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Bulletin scolaire généré avec succès", reportCard));
    }

    /**
     * Génère le bulletin scolaire d'un élève pour une séquence
     * 
     * @param studentId ID de l'élève
     * @param classRoomId ID de la classe
     * @param academicYearId ID de l'année académique
     * @param sequenceId ID de la séquence
     * @return Bulletin scolaire complet
     */
    @GetMapping("/student/{studentId}/class/{classRoomId}/academic-year/{academicYearId}/sequence/{sequenceId}")
    public ResponseEntity<ApiResponse<ReportCardDto>> getReportCardForSequence(
            @PathVariable UUID studentId,
            @PathVariable UUID classRoomId,
            @PathVariable UUID academicYearId,
            @PathVariable UUID sequenceId) {
        ReportCardDto reportCard = reportCardService.generateReportCardForSequence(studentId, classRoomId, academicYearId, sequenceId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Bulletin scolaire généré avec succès", reportCard));
    }

    /**
     * Génère les bulletins scolaires de tous les élèves d'une classe pour un trimestre
     * 
     * @param classRoomId ID de la classe
     * @param academicYearId ID de l'année académique
     * @param termId ID du trimestre
     * @return Liste des bulletins scolaires
     */
    @GetMapping("/class/{classRoomId}/academic-year/{academicYearId}/term/{termId}")
    public ResponseEntity<ApiResponse<List<ReportCardDto>>> getReportCardsForClassForTerm(
            @PathVariable UUID classRoomId,
            @PathVariable UUID academicYearId,
            @PathVariable UUID termId) {
        List<ReportCardDto> reportCards = reportCardService.generateReportCardsForClassForTerm(classRoomId, academicYearId, termId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Bulletins scolaires générés avec succès", reportCards));
    }

    /**
     * Génère les bulletins scolaires de tous les élèves d'une classe pour une séquence
     * 
     * @param classRoomId ID de la classe
     * @param academicYearId ID de l'année académique
     * @param sequenceId ID de la séquence
     * @return Liste des bulletins scolaires
     */
    @GetMapping("/class/{classRoomId}/academic-year/{academicYearId}/sequence/{sequenceId}")
    public ResponseEntity<ApiResponse<List<ReportCardDto>>> getReportCardsForClassForSequence(
            @PathVariable UUID classRoomId,
            @PathVariable UUID academicYearId,
            @PathVariable UUID sequenceId) {
        List<ReportCardDto> reportCards = reportCardService.generateReportCardsForClassForSequence(classRoomId, academicYearId, sequenceId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Bulletins scolaires générés avec succès", reportCards));
    }
}
