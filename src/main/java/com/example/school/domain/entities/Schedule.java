package com.example.school.domain.entities;

import lombok.Data;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Emploi du temps - Cours planifié
 * Représente un créneau horaire pour une classe, une matière et un enseignant
 * L'emploi du temps est lié à une année académique spécifique
 */
@Data
public class Schedule {
    private UUID id;
    private UUID classRoomId;      // Classe concernée
    private UUID classRoomSubjectId; // Association classe-matière (contient le coefficient)
    private UUID teacherSubjectId;  // Enseignant-matière
    private UUID schoolId;          // École (pour validation)
    private UUID academicYearId;    // Année académique
    
    private DayOfWeek dayOfWeek;   // Jour de la semaine (LUNDI, MARDI, etc.)
    private LocalTime startTime;   // Heure de début (ex: 08:00)
    private LocalTime endTime;     // Heure de fin (ex: 09:30)
    private String room;           // Salle de classe (optionnel)
    private String notes;          // Notes additionnelles (optionnel)
}

