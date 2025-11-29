package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Validator pour mettre à jour un créneau d'emploi du temps
 * Permet de modifier uniquement les champs nécessaires
 */
@Data
public class UpdateScheduleValidator {
    
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

