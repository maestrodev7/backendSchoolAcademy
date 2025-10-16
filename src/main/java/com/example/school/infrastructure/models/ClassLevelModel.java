package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "class_levels")
@Data
@NoArgsConstructor
public class ClassLevelModel {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
}
