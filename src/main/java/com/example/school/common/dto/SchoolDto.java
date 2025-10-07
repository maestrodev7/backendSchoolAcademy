package com.example.school.common.dto;

import com.example.school.domain.entities.AcademicYear;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class SchoolDto {
    private UUID id;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;

    private Set<AcademicYearDto> academicYears = new HashSet<>();
}
