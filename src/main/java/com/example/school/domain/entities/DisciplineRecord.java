package com.example.school.domain.entities;

import lombok.Data;
import java.util.UUID;

@Data
public class DisciplineRecord {
    private UUID id;
    private User student;
    private Term term;
    private ClassRoom classRoom;
    private Integer unjustifiedAbsencesHours;
    private Integer justifiedAbsencesHours;
    private Integer lateCount;
    private Integer detentionHours;
    private Boolean conductWarning;
    private Boolean conductBlame;
    private Integer exclusionDays;
    private Boolean permanentExclusion;
}

