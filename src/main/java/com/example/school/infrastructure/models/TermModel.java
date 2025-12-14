package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "terms")
@Data
@NoArgsConstructor
public class TermModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer number; // 1, 2, 3

    @Column(name = "academic_year_id", nullable = false)
    private UUID academicYearId;

    @Column(name = "school_id", nullable = false)
    private UUID schoolId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private boolean active = false;

    @OneToMany(mappedBy = "termId", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<SequenceModel> sequences;
}

