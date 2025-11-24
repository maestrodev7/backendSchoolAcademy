package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubjectRequestValidator {
    @NotBlank(message = "Le code de la matière est obligatoire")
    private String code;

    @NotBlank(message = "Le nom de la matière est obligatoire")
    private String name;

    private String description;
}

