package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClassLevelRequestValidator {
    @NotBlank(message = "Le nom du niveau de classe est obligatoire")
    private String name;
}
