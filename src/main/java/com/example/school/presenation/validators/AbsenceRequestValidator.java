package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class AbsenceRequestValidator {
    @NotNull(message = "L'ID de l'école est requis")
    private UUID schoolId;

    @NotNull(message = "L'ID de la classe est requis")
    private UUID classRoomId;

    @NotNull(message = "L'ID de la matière est requis")
    private UUID subjectId;

    @NotNull(message = "La date est requise")
    private LocalDate date;

    @NotEmpty(message = "La liste des élèves absents ne peut pas être vide")
    private List<UUID> absentStudentIds; // Liste des IDs des élèves absents

    @NotNull(message = "Le nombre d'heures est requis")
    @Positive(message = "Le nombre d'heures doit être positif")
    private Double numberOfHours;

    private UUID scheduleId; // Optionnel - ID du créneau d'emploi du temps
    private String notes;   // Optionnel - Notes additionnelles
}
