package com.example.school.domain.entities;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class StudentInfo {
    private UUID id;
    private User student;
    private String uniqueIdentifier; // Identifiant Unique
    private LocalDate birthDate;
    private String birthPlace;
    private String gender;
    private boolean isRepeating; // Redoublant
    private String parentNames;
    private String parentContacts;
    private String photoUrl;
}

