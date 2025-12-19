package com.example.school.presenation.validators;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class DisciplineRecordRequestValidator {
    @NotNull(message = "L'ID de l'élève est requis")
    private UUID studentId;

    @NotNull(message = "L'ID du trimestre est requis")
    private UUID termId;

    @NotNull(message = "L'ID de la classe est requis")
    private UUID classRoomId;

    private Integer unjustifiedAbsencesHours; // Abs. non. J. (h)

    private Integer justifiedAbsencesHours; // Abs just. (h)

    private Integer lateCount; // Retards (nombre de fois)

    private Integer detentionHours; // Consignes (heures)

    private Boolean conductWarning; // Avertissement de conduite

    private Boolean conductBlame; // Blâme de conduite

    private Integer exclusionDays; // Exclusions (jours)

    private Boolean permanentExclusion; // Exclusion définitive
}

