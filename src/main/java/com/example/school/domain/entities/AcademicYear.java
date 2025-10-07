package com.example.school.domain.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
public class AcademicYear {
    private UUID id;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    @ToString.Exclude
    @JsonIgnore
    private Set<School> schools;
}

