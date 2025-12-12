package com.example.school.presenation.validators;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class VerifyOtpRequestValidator {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "OTP code cannot be blank")
    @Pattern(regexp = "^\\d{6}$", message = "OTP code must be exactly 6 digits")
    private String code;

    @NotBlank(message = "Purpose cannot be blank")
    private String purpose; // "PASSWORD_RESET" or "ACCOUNT_CREATION"
}

