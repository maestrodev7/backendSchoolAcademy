package com.example.school.domain.services;

import com.example.school.common.dto.ReportCardDto;

import java.util.UUID;

public interface ReportCardServiceInterface {
    /**
     * Génère le bulletin scolaire d'un élève pour un trimestre
     * @param studentId ID de l'élève
     * @param classRoomId ID de la classe
     * @param academicYearId ID de l'année académique
     * @param termId ID du trimestre
     * @return Bulletin scolaire complet
     */
    ReportCardDto generateReportCardForTerm(UUID studentId, UUID classRoomId, UUID academicYearId, UUID termId);
    
    /**
     * Génère le bulletin scolaire d'un élève pour une séquence
     * @param studentId ID de l'élève
     * @param classRoomId ID de la classe
     * @param academicYearId ID de l'année académique
     * @param sequenceId ID de la séquence
     * @return Bulletin scolaire complet
     */
    ReportCardDto generateReportCardForSequence(UUID studentId, UUID classRoomId, UUID academicYearId, UUID sequenceId);
}
