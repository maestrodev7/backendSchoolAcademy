package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "series")
@Data
@NoArgsConstructor
public class SeriesModel {
    @Id
    @GeneratedValue
    private UUID id;

    private String code;
    private String name;
}
