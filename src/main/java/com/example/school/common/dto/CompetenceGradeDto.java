package com.example.school.common.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO pour une compétence avec sa note
 */
@Data
public class CompetenceGradeDto {
    private UUID gradeId;
    private UUID competenceId;
    private String competenceDescription;
    private Integer competenceOrderNumber; // Ordre d'affichage de la compétence
    
    // Notes
    private BigDecimal noteN20; // N/20
    private BigDecimal noteM20; // M/20
    private BigDecimal coefficient; // Coef de la compétence
    private BigDecimal mXCoef; // M x coef
    private BigDecimal cote; // COTE
    private BigDecimal minScore; // Min de la classe
    private BigDecimal maxScore; // Max de la classe
    private String appreciation; // Appréciation de l'enseignant
    private String teacherName; // Nom de l'enseignant
}
