package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "teacher_subjects", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_school_id", "subject_id", "school_id"}))
@Data
@NoArgsConstructor
public class TeacherSubjectModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_school_id", nullable = false)
    private UUID userSchoolId;

    @Column(name = "subject_id", nullable = false)
    private UUID subjectId;

    @Column(name = "school_id", nullable = false)
    private UUID schoolId;

    @Column(name = "academic_year_id", nullable = false)
    private UUID academicYearId;

    private String specialization;

    @Column(name = "experience_years")
    private Integer experienceYears;
}

