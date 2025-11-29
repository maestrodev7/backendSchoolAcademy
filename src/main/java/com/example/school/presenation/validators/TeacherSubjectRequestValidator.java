package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

/**
 * Validator pour créer ou mettre à jour une association Enseignant-Matière
 * Optimisé pour une excellente UX avec validation complète
 * Accepte soit userId soit userSchoolId pour plus de flexibilité
 */
@Data
public class TeacherSubjectRequestValidator {
    
    // Option 1: Passer directement l'ID de l'utilisateur (plus simple pour l'UX)
    private UUID userId;
    
    // Option 2: Passer l'ID de l'association User-School (si déjà connu)
    private UUID userSchoolId;
    
    @NotNull(message = "L'ID de la matière est obligatoire")
    private UUID subjectId;
    
    @NotNull(message = "L'ID de l'école est obligatoire")
    private UUID schoolId;
    
    @Size(max = 255, message = "La spécialisation ne peut pas dépasser 255 caractères")
    private String specialization;
    
    @PositiveOrZero(message = "Les années d'expérience doivent être positives ou nulles")
    private Integer experienceYears;
}

