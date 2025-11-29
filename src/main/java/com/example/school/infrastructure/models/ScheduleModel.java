package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Modèle JPA pour l'emploi du temps
 */
@Entity
@Table(name = "schedules",
       uniqueConstraints = @UniqueConstraint(
           columnNames = {"class_room_id", "day_of_week", "start_time", "end_time"}
       ))
@Data
@NoArgsConstructor
public class ScheduleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "class_room_id", nullable = false)
    private UUID classRoomId;

    @Column(name = "class_room_subject_id", nullable = false)
    private UUID classRoomSubjectId;

    @Column(name = "teacher_subject_id", nullable = false)
    private UUID teacherSubjectId;

    @Column(name = "school_id", nullable = false)
    private UUID schoolId;

    @Column(name = "academic_year_id", nullable = false)
    private UUID academicYearId;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    private String room;

    @Column(columnDefinition = "TEXT")
    private String notes;
}

