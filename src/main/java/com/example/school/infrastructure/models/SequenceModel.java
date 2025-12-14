package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "sequences")
@Data
@NoArgsConstructor
public class SequenceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer number; // Numéro global (1, 2, 3, 4, 5, 6)

    @Column(name = "term_id", nullable = false)
    private UUID termId;

    @Column(name = "school_id", nullable = false)
    private UUID schoolId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private boolean active = false;
}

