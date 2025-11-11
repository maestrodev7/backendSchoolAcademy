package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class StudentRegistrationRequestValidator {

    @NotNull(message = "L'identifiant de l'élève est obligatoire")
    private UUID studentId;

    @NotNull(message = "L'identifiant de la classe est obligatoire")
    private UUID classRoomId;

    @NotNull(message = "L'identifiant de l'année académique est obligatoire")
    private UUID academicYearId;
}

