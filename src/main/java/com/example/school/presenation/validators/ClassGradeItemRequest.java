package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ClassGradeItemRequest {

    @NotNull(message = "L'ID de l'élève est requis")
    private UUID studentId;

    // Champs de note (tous optionnels pour permettre 0 ou null)
    private BigDecimal noteN20;
    private BigDecimal noteM20;
    private BigDecimal coefficient;
    private BigDecimal cote;
    private BigDecimal minScore;
    private BigDecimal maxScore;
    private String appreciation;
}

