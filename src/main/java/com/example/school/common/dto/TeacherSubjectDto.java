package com.example.school.common.dto;

import lombok.Data;
import java.util.UUID;

/**
 * DTO pour l'association Enseignant-Matière-École
 * Enrichi avec les informations de l'enseignant et de la matière pour une meilleure UX
 */
@Data
public class TeacherSubjectDto {
    private UUID id;
    private UUID userSchoolId;
    private UUID subjectId;
    private UUID schoolId;
    
    // Informations enrichies pour l'UX
    private UserDto teacher;           // Informations complètes de l'enseignant
    private SubjectDto subject;         // Informations complètes de la matière
    private SchoolDto school;           // Informations de l'école
    
    // Métadonnées
    private String specialization;     // Spécialisation (ex: "Mathématiques avancées")
    private Integer experienceYears;     // Années d'expérience dans cette matière
}

