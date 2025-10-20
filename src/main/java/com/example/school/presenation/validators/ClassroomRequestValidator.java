package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ClassroomRequestValidator {

    @NotBlank(message = "Le nom de la classe est obligatoire")
    private String label;

    @NotNull(message = "Le niveau de classe est obligatoire")
    private UUID classLevelId;

    @NotNull(message = "La série est obligatoire")
    private UUID seriesId;

    @NotNull(message = "L'école est obligatoire") // ✅ Ajouté
    private UUID schoolId;
}
