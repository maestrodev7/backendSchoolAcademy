package com.example.school.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "academic_years")
@Data
@NoArgsConstructor
@ToString(exclude = "schools")
@EqualsAndHashCode(exclude = "schools")
public class AcademicYearModel {
    @Id
    @GeneratedValue
    private UUID id;

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    @ManyToMany(mappedBy = "academicYears")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Set<SchoolModel> schools;
}
