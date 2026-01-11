package com.example.school.common.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * DTO pour le bulletin scolaire d'un élève
 * Contient toutes les informations nécessaires pour générer un bulletin PDF
 */
@Data
public class ReportCardDto {
    // Informations de l'élève
    private UUID studentId;
    private String studentName;
    private String studentUniqueIdentifier;
    private java.time.LocalDate studentBirthDate;
    private String studentBirthPlace;
    private String studentGender;
    private Boolean studentIsRepeating;
    private String studentPhotoUrl;
    
    // Informations parentales
    private String parentNames;
    private String parentContacts;
    
    // Informations de la classe et année académique
    private UUID classRoomId;
    private String classRoomLabel;
    private UUID schoolId;
    private String schoolName;
    private String schoolAddress;
    private String schoolPhoneNumber;
    private String schoolEmail;
    private UUID academicYearId;
    private String academicYearLabel;
    
    // Période (trimestre ou séquence)
    private UUID termId;
    private String termName;
    private UUID sequenceId;
    private String sequenceName;
    
    // Notes
    private List<GradeDto> grades;
    
    // Statistiques des notes
    private BigDecimal averageScore; // Moyenne générale
    private BigDecimal totalCoefficient; // Total des coefficients
    private Integer totalGrades; // Nombre de notes
    
    // Record disciplinaire
    private DisciplineRecordDto disciplineRecord;
    
    // Absences (optionnel - total d'heures pour la période)
    private Double totalAbsenceHours;
    
    // Date de génération
    private java.time.LocalDate generatedDate;
}
