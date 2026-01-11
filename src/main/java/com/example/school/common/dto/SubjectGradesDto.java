package com.example.school.common.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * DTO pour regrouper les notes d'une matière avec toutes ses compétences
 */
@Data
public class SubjectGradesDto {
    private UUID subjectId;
    private String subjectName;
    private String subjectCode;
    private BigDecimal subjectCoefficient; // Coefficient de la matière pour la classe
    private String teacherName; // Nom de l'enseignant de la matière
    
    // Liste des compétences avec leurs notes
    private List<CompetenceGradeDto> competences;
    
    // Statistiques de la matière
    private BigDecimal totalScore; // TOTAL (somme de toutes les notes M/20)
    private BigDecimal averageScore; // MOYENNE de la matière (moyenne des notes M/20)
    private BigDecimal totalCoefficient; // Total des coefficients des compétences
    private BigDecimal weightedAverage; // MOYENNE x COEFFICIENT (moyenne pondérée)
    private BigDecimal cote; // COTE (note finale de la matière)
    private BigDecimal minScore; // Min de la classe pour cette matière
    private BigDecimal maxScore; // Max de la classe pour cette matière
}
