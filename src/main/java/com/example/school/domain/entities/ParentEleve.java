package com.example.school.domain.entities;

import lombok.Data;

import java.util.UUID;

@Data
public class ParentEleve {
    private UUID id;
    private UUID parentId;
    private UUID eleveId;
}
