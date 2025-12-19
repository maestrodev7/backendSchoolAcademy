package com.example.school.common.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class StudentInfoDto {
    private UUID id;
    private UUID studentId;
    private String studentName;
    private String uniqueIdentifier;
    private LocalDate birthDate;
    private String birthPlace;
    private String gender;
    private Boolean isRepeating;
    private String parentNames;
    private String parentContacts;
    private String photoUrl;
}

