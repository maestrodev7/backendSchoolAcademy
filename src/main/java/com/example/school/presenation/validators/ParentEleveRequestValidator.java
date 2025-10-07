package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ParentEleveRequestValidator {

    @NotNull(message = "Parent ID cannot be null")
    private UUID parentId;

    @NotNull(message = "Élève ID cannot be null")
    private UUID eleveId;
}
