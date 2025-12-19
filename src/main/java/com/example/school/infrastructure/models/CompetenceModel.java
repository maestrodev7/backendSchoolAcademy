package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "competences")
@Data
@NoArgsConstructor
public class CompetenceModel {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectModel subject;

    @Column(nullable = false, length = 1000)
    private String description; // Description de la compétence évaluée

    @Column(name = "order_number")
    private Integer orderNumber; // Ordre d'affichage dans le bulletin
}

