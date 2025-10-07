package com.example.school.common.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ParentEleveDto {
    private UUID id;
    private UUID parentId;
    private UUID eleveId;
}
