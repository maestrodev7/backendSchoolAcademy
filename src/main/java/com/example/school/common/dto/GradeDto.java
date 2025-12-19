package com.example.school.common.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class GradeDto {
    private UUID id;
    private UUID competenceId;
    private String competenceDescription;
    private UUID subjectId;
    private String subjectName;
    private UUID studentId;
    private String studentName;
    private UUID termId;
    private String termName;
    private UUID sequenceId;
    private String sequenceName;
    private BigDecimal noteN20;
    private BigDecimal noteM20;
    private BigDecimal coefficient;
    private BigDecimal mXCoef;
    private BigDecimal cote;
    private BigDecimal minScore;
    private BigDecimal maxScore;
    private String appreciation;
    private UUID teacherId;
    private String teacherName;
}

