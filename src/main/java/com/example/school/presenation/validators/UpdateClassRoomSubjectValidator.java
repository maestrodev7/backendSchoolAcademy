package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateClassRoomSubjectValidator {
    @NotNull(message = "Le coefficient est obligatoire")
    @Positive(message = "Le coefficient doit être positif")
    private Double coefficient;
}

