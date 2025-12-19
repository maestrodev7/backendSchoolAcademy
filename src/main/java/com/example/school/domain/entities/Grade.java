package com.example.school.domain.entities;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class Grade {
    private UUID id;
    private Competence competence;
    private User student;
    private Term term;
    private Sequence sequence;
    private BigDecimal noteN20; // Note N/20
    private BigDecimal noteM20; // Note M/20
    private BigDecimal coefficient;
    private BigDecimal mXCoef; // M x coefficient
    private BigDecimal cote; // COTE
    private BigDecimal minScore; // Min de la classe
    private BigDecimal maxScore; // Max de la classe
    private String appreciation;
    private User teacher;
}

