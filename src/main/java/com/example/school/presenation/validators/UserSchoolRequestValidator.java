package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class UserSchoolRequestValidator {

    @NotNull(message = "User ID cannot be null")
    private UUID userId;

    @NotNull(message = "School ID cannot be null")
    private UUID schoolId;

    @NotBlank(message = "Role cannot be blank")
    private String role;
}
