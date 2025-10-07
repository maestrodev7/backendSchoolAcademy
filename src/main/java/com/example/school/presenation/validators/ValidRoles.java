package com.example.school.presenation.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RolesValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRoles {
    String message() default "roles' doit être un tableau, par ex. [\"SUPER_ADMIN\", \"ADMIN\"] parmis  : SUPER_ADMIN, ADMIN, PROMOTEUR, ENSEIGNANT, ELEVE, PARENT";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}