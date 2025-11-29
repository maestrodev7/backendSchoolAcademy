package com.example.school.common.dto;

import lombok.Data;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

/**
 * DTO pour l'emploi du temps
 * Enrichi avec toutes les informations nécessaires pour une excellente UX
 */
@Data
public class ScheduleDto {
    private UUID id;
    private UUID classRoomId;
    private UUID classRoomSubjectId;
    private UUID teacherSubjectId;
    private UUID schoolId;
    
    // Informations enrichies pour l'UX
    private ClassroomDto classRoom;           // Informations complètes de la classe
    private ClassRoomSubjectDto classRoomSubject; // Association classe-matière avec coefficient
    private TeacherSubjectDto teacherSubject;   // Enseignant-matière avec détails
    private SchoolDto school;                  // Informations de l'école
    
    // Horaires
    private DayOfWeek dayOfWeek;              // Jour de la semaine
    private LocalTime startTime;              // Heure de début
    private LocalTime endTime;                 // Heure de fin
    private String duration;                   // Durée calculée (ex: "1h30")
    
    // Informations additionnelles
    private String room;                       // Salle de classe
    private String notes;                      // Notes additionnelles
}

