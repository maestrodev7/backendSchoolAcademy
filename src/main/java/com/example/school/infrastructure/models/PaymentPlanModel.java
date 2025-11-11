package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "payment_plans")
@Data
@NoArgsConstructor
public class PaymentPlanModel {
    @Id
    @GeneratedValue
    private UUID id;

    private String label; // Exemple : "1ère Tranche", "2e Tranche"
    private LocalDate dueDate; // Date limite de paiement
    private int orderIndex; // Pour garder l’ordre (1, 2, 3...)

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolModel school;
}
