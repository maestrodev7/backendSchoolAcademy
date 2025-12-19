package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "grades")
@Data
@NoArgsConstructor
public class GradeModel {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "competence_id", nullable = false)
    private CompetenceModel competence;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private UserModel student;

    @ManyToOne
    @JoinColumn(name = "term_id", nullable = false)
    private TermModel term; // Trimestre

    @ManyToOne
    @JoinColumn(name = "sequence_id")
    private SequenceModel sequence; // Séquence (optionnel, peut être null si note trimestrielle)

    @Column(name = "note_n_20", precision = 5, scale = 2)
    private BigDecimal noteN20; // Note N/20

    @Column(name = "note_m_20", precision = 5, scale = 2)
    private BigDecimal noteM20; // Note M/20

    @Column(name = "coefficient", precision = 5, scale = 2)
    private BigDecimal coefficient; // Coefficient de la compétence

    @Column(name = "m_x_coef", precision = 5, scale = 2)
    private BigDecimal mXCoef; // M x coefficient

    @Column(name = "cote", precision = 5, scale = 2)
    private BigDecimal cote; // COTE

    @Column(name = "min_score", precision = 5, scale = 2)
    private BigDecimal minScore; // Min de la classe

    @Column(name = "max_score", precision = 5, scale = 2)
    private BigDecimal maxScore; // Max de la classe

    @Column(name = "appreciation", length = 1000)
    private String appreciation; // Appréciation de l'enseignant

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private UserModel teacher; // Enseignant qui a donné la note
}

