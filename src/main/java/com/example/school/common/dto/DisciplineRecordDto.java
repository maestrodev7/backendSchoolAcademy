package com.example.school.common.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class DisciplineRecordDto {
    private UUID id;
    private UUID studentId;
    private String studentName;
    private UUID termId;
    private String termName;
    private UUID classRoomId;
    private String classRoomLabel;
    private Integer unjustifiedAbsencesHours;
    private Integer justifiedAbsencesHours;
    private Integer lateCount;
    private Integer detentionHours;
    private Boolean conductWarning;
    private Boolean conductBlame;
    private Integer exclusionDays;
    private Boolean permanentExclusion;
}

