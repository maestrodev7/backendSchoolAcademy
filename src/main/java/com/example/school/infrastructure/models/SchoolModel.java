package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "schools")
@Data
@NoArgsConstructor
@ToString(exclude = "academicYears")
@EqualsAndHashCode(exclude = "academicYears")
public class SchoolModel {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String address;
    private String email;
    private String phoneNumber;

    @ManyToMany
    @JoinTable(
            name = "school_academic_year",
            joinColumns = @JoinColumn(name = "school_id"),
            inverseJoinColumns = @JoinColumn(name = "academic_year_id")
    )
    @com.fasterxml.jackson.annotation.JsonManagedReference
    private Set<AcademicYearModel> academicYears;


}

