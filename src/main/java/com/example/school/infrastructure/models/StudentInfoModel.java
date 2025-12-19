package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "student_infos")
@Data
@NoArgsConstructor
public class StudentInfoModel {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    private UserModel student;

    @Column(name = "unique_identifier", unique = true)
    private String uniqueIdentifier; // Identifiant Unique

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "birth_place")
    private String birthPlace;

    @Column(name = "gender")
    private String gender; // M, F, etc.

    @Column(name = "is_repeating")
    private boolean isRepeating; // Redoublant

    @Column(name = "parent_names")
    private String parentNames; // Noms des parents/tuteurs

    @Column(name = "parent_contacts")
    private String parentContacts; // Contacts des parents/tuteurs

    @Column(name = "photo_url")
    private String photoUrl; // URL de la photo de l'élève
}

