package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "student_registrations")
@Data
@NoArgsConstructor
public class StudentRegistrationModel {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private UserModel student; // (à créer si pas encore existant)

    @ManyToOne
    @JoinColumn(name = "class_room_id")
    private ClassRoomModel classRoom;

    @ManyToOne
    @JoinColumn(name = "academic_year_id")
    private AcademicYearModel academicYear;

    private boolean isConfirmed; // devient vrai après paiement des frais d’inscription
}
