package com.example.school.domain.entities;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Enregistrement d'absence d'un élève pour un cours particulier
 */
@Data
public class Absence {
    private UUID id;
    private User student;           // Élève absent
    private ClassRoom classRoom;    // Classe
    private School school;          // École
    private Subject subject;        // Matière/cours
    private AcademicYear academicYear; // Année académique
    private UUID scheduleId;        // ID de l'emploi du temps (optionnel, pour lier à un créneau spécifique)
    private LocalDate date;         // Date du cours
    private Double numberOfHours;   // Nombre d'heures d'absence
    private String notes;           // Notes additionnelles (optionnel)
}
