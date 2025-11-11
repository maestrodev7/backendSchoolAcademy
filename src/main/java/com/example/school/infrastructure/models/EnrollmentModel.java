package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "enrollments")
@Data
@NoArgsConstructor
public class EnrollmentModel {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private UserModel student;

    @ManyToOne
    @JoinColumn(name = "class_room_id")
    private ClassRoomModel classRoom;

    @ManyToOne
    @JoinColumn(name = "academic_year_id")
    private AcademicYearModel academicYear;

    private boolean validated; // true si frais d’inscription payés
}
