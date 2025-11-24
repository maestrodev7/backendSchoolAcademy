package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Table de jointure entre Subject et ClassRoom
 * Stocke le coefficient de la matière pour chaque classe
 */
@Entity
@Table(name = "class_room_subjects", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"class_room_id", "subject_id"}))
@Data
@NoArgsConstructor
public class ClassRoomSubjectModel {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonBackReference
    private SubjectModel subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_room_id", nullable = false)
    private ClassRoomModel classRoom;

    @Column(nullable = false)
    private Double coefficient;
}

