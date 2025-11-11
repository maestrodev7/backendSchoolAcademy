package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FeeTypeRequestValidator {

    @NotBlank(message = "Le libellé du frais est obligatoire")
    private String name;

    private boolean mandatory;
}

