package com.example.school.presenation.validators;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentPlanRequestValidator {

    @NotBlank(message = "Le libellé de la tranche est obligatoire")
    private String label;

    private LocalDate dueDate;

    @NotNull(message = "L'ordre de la tranche est obligatoire")
    @Min(value = 1, message = "L'ordre doit être supérieur ou égal à 1")
    private Integer orderIndex;
}

