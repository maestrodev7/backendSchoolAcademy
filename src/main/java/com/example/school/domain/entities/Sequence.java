package com.example.school.domain.entities;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Séquence - Sous-période d'un trimestre
 * Les séquences sont numérotées globalement (1, 2, 3, 4, 5, 6) mais appartiennent à des trimestres
 * Exemple : Trimestre 1 -> Séquences 1 et 2, Trimestre 2 -> Séquences 3 et 4, etc.
 */
@Data
public class Sequence {
    private UUID id;
    private String name; // Ex: "Séquence 1", "Séquence 2"
    private Integer number; // Numéro global de la séquence (1, 2, 3, 4, 5, 6)
    private UUID termId; // Trimestre auquel appartient cette séquence
    private UUID schoolId; // École à laquelle appartient cette séquence
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active; // Indique si c'est la séquence active
}

