package com.example.school.domain.entities;

import lombok.Data;
import java.util.UUID;

/**
 * Association entre un enseignant, une école et une matière
 * Permet de définir quels enseignants enseignent quelles matières dans quelle école
 * L'association est liée à une année académique spécifique
 */
@Data
public class TeacherSubject {
    private UUID id;
    private UUID userSchoolId;    // ID de l'association User-School (enseignant dans une école)
    private UUID schoolId;        // ID de l'école
    private UUID subjectId;       // ID de la matière
    private UUID academicYearId;  // ID de l'année académique
    private String specialization;     // Spécialisation (ex: "Mathématiques avancées")
    private Integer experienceYears;   // Années d'expérience dans cette matière
}
