package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "term_reports")
@Data
@NoArgsConstructor
public class TermReportModel {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private UserModel student;

    @ManyToOne
    @JoinColumn(name = "term_id", nullable = false)
    private TermModel term;

    @ManyToOne
    @JoinColumn(name = "class_room_id", nullable = false)
    private ClassRoomModel classRoom;

    @ManyToOne
    @JoinColumn(name = "academic_year_id", nullable = false)
    private AcademicYearModel academicYear;

    // Résultats globaux
    @Column(name = "total_general", precision = 10, scale = 2)
    private BigDecimal totalGeneral; // TOTAL GENERAL

    @Column(name = "total_coefficient", precision = 10, scale = 2)
    private BigDecimal totalCoefficient; // COEF

    @Column(name = "term_average", precision = 5, scale = 2)
    private BigDecimal termAverage; // MOYENNE TRIM

    @Column(name = "cote", precision = 5, scale = 2)
    private BigDecimal cote; // COTE

    // Appréciations
    @Column(name = "student_work_appreciation", length = 2000)
    private String studentWorkAppreciation; // Appréciation du travail de l'élève

    // Profil de la classe
    @Column(name = "class_average", precision = 5, scale = 2)
    private BigDecimal classAverage; // Moyenne générale de la classe

    @Column(name = "class_min", precision = 5, scale = 2)
    private BigDecimal classMin; // Min de la classe

    @Column(name = "class_max", precision = 5, scale = 2)
    private BigDecimal classMax; // Max de la classe

    @Column(name = "number_of_averages")
    private Integer numberOfAverages; // Nombre de moyennes

    @Column(name = "success_rate", precision = 5, scale = 2)
    private BigDecimal successRate; // Taux de réussite

    // Visas
    @Column(name = "parent_visa")
    private String parentVisa; // Visa du parent/tuteur

    @Column(name = "principal_teacher_visa")
    private String principalTeacherVisa; // Nom et visa du professeur principal

    @ManyToOne
    @JoinColumn(name = "principal_teacher_id")
    private UserModel principalTeacher; // Professeur principal

    // Relation avec le record disciplinaire
    @OneToOne
    @JoinColumn(name = "discipline_record_id")
    private DisciplineRecordModel disciplineRecord;
}

