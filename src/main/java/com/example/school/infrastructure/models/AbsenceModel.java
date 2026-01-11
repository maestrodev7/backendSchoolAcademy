package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "absences")
@Data
@NoArgsConstructor
public class AbsenceModel {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private UserModel student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_room_id", nullable = false)
    private ClassRoomModel classRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private SchoolModel school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectModel subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_year_id", nullable = false)
    private AcademicYearModel academicYear;

    @Column(name = "schedule_id")
    private UUID scheduleId; // Optionnel - ID du créneau d'emploi du temps

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "number_of_hours", nullable = false)
    private Double numberOfHours;

    @Column(name = "notes", length = 1000)
    private String notes;
}
