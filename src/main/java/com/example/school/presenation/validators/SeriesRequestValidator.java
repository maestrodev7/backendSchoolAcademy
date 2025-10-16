package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SeriesRequestValidator {
    @NotBlank(message = "Le code de la série est obligatoire")
    private String code;

    @NotBlank(message = "Le nom de la série est obligatoire")
    private String name;
}
