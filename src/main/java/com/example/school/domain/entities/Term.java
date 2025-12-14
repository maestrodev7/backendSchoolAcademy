package com.example.school.domain.entities;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * Trimestre - Période académique divisant l'année scolaire
 * Exemple : Trimestre 1, Trimestre 2, Trimestre 3
 */
@Data
public class Term {
    private UUID id;
    private String name; // Ex: "Trimestre 1", "Trimestre 2", "Trimestre 3"
    private Integer number; // 1, 2, 3
    private UUID academicYearId; // Année académique à laquelle appartient ce trimestre
    private UUID schoolId; // École à laquelle appartient ce trimestre
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active; // Indique si c'est le trimestre actif

    @ToString.Exclude
    private Set<Sequence> sequences; // Les séquences de ce trimestre
}

