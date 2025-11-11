package com.example.school.presenation.validators;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ClassFeeRequestValidator {

    @NotNull(message = "La classe est obligatoire")
    private UUID classRoomId;

    @NotNull(message = "Le type de frais est obligatoire")
    private UUID feeTypeId;

    private UUID paymentPlanId;

    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être supérieur à zéro")
    private BigDecimal amount;

    private LocalDate dueDate;
}

