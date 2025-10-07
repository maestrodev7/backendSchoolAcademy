package com.example.school.presenation.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhoneNumber {
    String message() default "Phone number must be empty or start with +237 followed by 9 digits (e.g. +237696246686)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}