package com.example.school.domain.entities;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TermReport {
    private UUID id;
    private User student;
    private Term term;
    private ClassRoom classRoom;
    private AcademicYear academicYear;
    private BigDecimal totalGeneral;
    private BigDecimal totalCoefficient;
    private BigDecimal termAverage;
    private BigDecimal cote;
    private String studentWorkAppreciation;
    private BigDecimal classAverage;
    private BigDecimal classMin;
    private BigDecimal classMax;
    private Integer numberOfAverages;
    private BigDecimal successRate;
    private String parentVisa;
    private String principalTeacherVisa;
    private User principalTeacher;
    private DisciplineRecord disciplineRecord;
}

