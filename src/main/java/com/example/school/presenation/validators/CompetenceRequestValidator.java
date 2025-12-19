package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class CompetenceRequestValidator {
    @NotNull(message = "L'ID de la matière est requis")
    private UUID subjectId;

    @NotBlank(message = "La description de la compétence est requise")
    private String description;

    private Integer orderNumber;
}

