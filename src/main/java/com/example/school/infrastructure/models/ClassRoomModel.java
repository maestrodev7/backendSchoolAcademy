package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "class_rooms")
@Data
@NoArgsConstructor
public class ClassRoomModel {
    @Id
    @GeneratedValue
    private UUID id;

    private String label;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolModel school;

    @ManyToOne
    @JoinColumn(name = "academic_year_id")
    private AcademicYearModel academicYear;

    @ManyToOne
    @JoinColumn(name = "class_level_id")
    private ClassLevelModel classLevel;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private SeriesModel series;
}
