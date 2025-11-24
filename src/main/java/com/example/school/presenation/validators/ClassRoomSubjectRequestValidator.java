package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class ClassRoomSubjectRequestValidator {
    @NotNull(message = "L'ID de la matière est obligatoire")
    private UUID subjectId;

    @NotNull(message = "L'ID de la classe est obligatoire")
    private UUID classRoomId;

    @NotNull(message = "Le coefficient est obligatoire")
    @Positive(message = "Le coefficient doit être positif")
    private Double coefficient;
}

