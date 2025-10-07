package com.example.school.presenation.validators;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestValidator {

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}
