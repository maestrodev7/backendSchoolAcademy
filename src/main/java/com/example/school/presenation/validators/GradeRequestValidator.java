package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class GradeRequestValidator {
    @NotNull(message = "L'ID de la compétence est requis")
    private UUID competenceId;

    @NotNull(message = "L'ID de l'élève est requis")
    private UUID studentId;

    @NotNull(message = "L'ID du trimestre est requis")
    private UUID termId;

    private UUID sequenceId; // Optionnel

    private BigDecimal noteN20; // Note N/20

    private BigDecimal noteM20; // Note M/20

    private BigDecimal coefficient;

    private BigDecimal mXCoef; // M x coefficient

    private BigDecimal cote; // COTE

    private BigDecimal minScore; // Min de la classe

    private BigDecimal maxScore; // Max de la classe

    private String appreciation; // Appréciation de l'enseignant

    private UUID teacherId; // Enseignant qui a donné la note
}

