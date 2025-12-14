package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class SequenceRequestValidator {
    @NotBlank(message = "Le nom de la séquence ne peut pas être vide")
    private String name;

    @NotNull(message = "Le numéro de la séquence est requis")
    @Min(value = 1, message = "Le numéro de la séquence doit être au moins 1")
    private Integer number;

    @NotNull(message = "L'ID du trimestre est requis")
    private UUID termId;

    @NotNull(message = "L'ID de l'école est requis")
    private UUID schoolId;

    @NotNull(message = "La date de début est requise")
    private LocalDate startDate;

    @NotNull(message = "La date de fin est requise")
    private LocalDate endDate;
}

