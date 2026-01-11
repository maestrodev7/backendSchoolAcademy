package com.example.school.common.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class AbsenceDto {
    private UUID id;
    private UUID studentId;
    private String studentName;
    private UUID classRoomId;
    private String classRoomLabel;
    private UUID schoolId;
    private String schoolName;
    private UUID subjectId;
    private String subjectName;
    private String subjectCode;
    private UUID academicYearId;
    private String academicYearLabel;
    private UUID scheduleId;
    private LocalDate date;
    private Double numberOfHours;
    private String notes;
}
