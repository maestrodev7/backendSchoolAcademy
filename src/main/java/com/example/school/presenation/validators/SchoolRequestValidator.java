package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SchoolRequestValidator {

    @NotBlank(message = "School name cannot be blank")
    @Size(min = 2, max = 100, message = "School name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Address cannot be blank")
    @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")
    private String address;

    private String phoneNumber;

    private String email;

    @NotEmpty(message = "At least one academic year must be provided")
    private List<UUID> academicYearIds;
}
