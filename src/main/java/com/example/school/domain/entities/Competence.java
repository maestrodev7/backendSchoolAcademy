package com.example.school.domain.entities;

import lombok.Data;
import java.util.UUID;

@Data
public class Competence {
    private UUID id;
    private Subject subject;
    private String description;
    private Integer orderNumber;
}

