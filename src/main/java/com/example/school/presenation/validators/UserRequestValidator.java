package com.example.school.presenation.validators;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UserRequestValidator {

    @NotBlank(message = "Username cannot be blank")
    private String username;

    private String firstName;

    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @ValidPhoneNumber
    private String phoneNumber;

    @ValidRoles
    @NotEmpty(message = "Roles cannot be empty")
    private List<@NotBlank(message = "Role name cannot be blank") String> roles;
}
