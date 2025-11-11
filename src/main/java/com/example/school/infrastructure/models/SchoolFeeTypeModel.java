package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "school_fee_types")
@Data
@NoArgsConstructor
public class SchoolFeeTypeModel {

    @Id
    @GeneratedValue
    private UUID id;

    private String label; // Exemple: "Inscription", "APE", "Dossier", "Tranche"

    private boolean mandatory; // true si obligatoire (ex: Inscription)

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolModel school;
}
