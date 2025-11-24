package com.example.school.domain.entities;

import lombok.Data;
import java.util.UUID;

/**
 * Table de jointure entre Subject et ClassRoom
 * Permet de stocker le coefficient de la matière pour chaque classe
 */
@Data
public class ClassRoomSubject {
    private UUID id;
    private Subject subject;
    private ClassRoom classRoom;
    private Double coefficient; // Le coefficient dépend de la classe
}

