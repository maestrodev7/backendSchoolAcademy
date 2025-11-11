package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "fee_types")
@Data
@NoArgsConstructor
public class FeeTypeModel {
    @Id
    @GeneratedValue
    private UUID id;

    private String name; // Exemple : INSCRIPTION, APE, DOSSIER, SCOLARITE
    private boolean mandatory; // true si obligatoire pour l’école

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolModel school;
}
