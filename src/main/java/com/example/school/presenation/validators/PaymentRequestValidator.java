package com.example.school.presenation.validators;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class PaymentRequestValidator {

    @NotNull(message = "La tranche ou le frais ciblé est obligatoire")
    private UUID classFeeId;

    @NotNull(message = "Le montant payé est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être supérieur à zéro")
    private BigDecimal amountPaid;

    private LocalDate paymentDate;
}

