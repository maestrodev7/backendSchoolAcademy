package com.example.school.presenation.validators;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ClassGradesBulkRequest {

    @NotNull(message = "L'ID de la compétence est requis")
    private UUID competenceId;

    @NotNull(message = "L'ID du trimestre est requis")
    private UUID termId;

    // Optionnel : pour une séquence particulière du trimestre
    private UUID sequenceId;

    // Optionnel : enseignant qui saisit les notes
    private UUID teacherId;

    @NotNull(message = "La liste des notes de la classe est requise")
    private List<@Valid ClassGradeItemRequest> grades;
}


