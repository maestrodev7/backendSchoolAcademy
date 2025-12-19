package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class StudentInfoRequestValidator {
    @NotNull(message = "L'ID de l'élève est requis")
    private UUID studentId;

    // parentId et classRoomId sont requis pour la création, optionnels pour la mise à jour
    private UUID parentId;

    private UUID classRoomId;

    private LocalDate birthDate;

    private String birthPlace;

    private String gender; // M, F, etc.

    private Boolean isRepeating; // Redoublant

    private String photoUrl;
}

