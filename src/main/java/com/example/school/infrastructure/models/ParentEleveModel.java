package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "parent_eleve")
@Data
@NoArgsConstructor
public class ParentEleveModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "parent_id", nullable = false)
    private UUID parentId;


    @Column(name = "eleve_id", nullable = false)
    private UUID eleveId;
}
