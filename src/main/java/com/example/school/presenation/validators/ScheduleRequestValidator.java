package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Validator pour créer ou mettre à jour un créneau d'emploi du temps
 * Optimisé pour une excellente UX avec validation complète
 * Accepte soit classRoomSubjectId soit classRoomId+subjectId pour plus de flexibilité
 */
@Data
public class ScheduleRequestValidator {
    
    @NotNull(message = "L'ID de la classe est obligatoire")
    private UUID classRoomId;
    
    // Option 1: Passer directement l'ID de l'association classe-matière
    private UUID classRoomSubjectId;
    
    // Option 2: Passer l'ID de la matière (si classRoomSubjectId n'est pas fourni)
    private UUID subjectId;
    
    @NotNull(message = "L'ID de l'association enseignant-matière est obligatoire")
    private UUID teacherSubjectId;
    
    @NotNull(message = "L'ID de l'école est obligatoire")
    private UUID schoolId;
    
    @NotNull(message = "Le jour de la semaine est obligatoire")
    private DayOfWeek dayOfWeek;
    
    @NotNull(message = "L'heure de début est obligatoire")
    private LocalTime startTime;
    
    @NotNull(message = "L'heure de fin est obligatoire")
    private LocalTime endTime;
    
    @Size(max = 100, message = "Le nom de la salle ne peut pas dépasser 100 caractères")
    private String room;
    
    @Size(max = 1000, message = "Les notes ne peuvent pas dépasser 1000 caractères")
    private String notes;
}

