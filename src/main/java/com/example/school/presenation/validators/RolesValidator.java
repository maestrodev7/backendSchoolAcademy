package com.example.school.presenation.validators;


import com.example.school.domain.entities.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class RolesValidator implements ConstraintValidator<ValidRoles, List<String>> {

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (values == null || values.isEmpty()) return false;
        return values.stream()
                .allMatch(v -> Arrays.stream(Role.values())
                        .anyMatch(role -> role.name().equalsIgnoreCase(v)));
    }
}

