package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "discipline_records")
@Data
@NoArgsConstructor
public class DisciplineRecordModel {
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

    // Absences
    @Column(name = "unjustified_absences_hours")
    private Integer unjustifiedAbsencesHours; // Abs. non. J. (h)

    @Column(name = "justified_absences_hours")
    private Integer justifiedAbsencesHours; // Abs just. (h)

    // Retards
    @Column(name = "late_count")
    private Integer lateCount; // Retards (nombre de fois)

    // Consignes
    @Column(name = "detention_hours")
    private Integer detentionHours; // Consignes (heures)

    // Sanctions
    @Column(name = "conduct_warning")
    private Boolean conductWarning; // Avertissement de conduite

    @Column(name = "conduct_blame")
    private Boolean conductBlame; // Blâme de conduite

    @Column(name = "exclusion_days")
    private Integer exclusionDays; // Exclusions (jours)

    @Column(name = "permanent_exclusion")
    private Boolean permanentExclusion; // Exclusion définitive
}

